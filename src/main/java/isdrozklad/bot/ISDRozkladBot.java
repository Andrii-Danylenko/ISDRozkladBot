package isdrozklad.bot;

import isdrozklad.entities.UserData;
import isdrozklad.logic.DAOImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ISDRozkladBot extends TelegramLongPollingBot {
    private final DAOImpl dao = new DAOImpl();
    private String previousMessage = "";
    private UserData userData;
    private final Map<Long, Boolean> isWaitingForReplyMap = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                processUpdate(update);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processUpdate(Update update) throws TelegramApiException {
            String receivedText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(receivedText);
            switch (receivedText.toLowerCase()) {
                case ("/start"), ("/help") -> {
                    sendPhoto(chatId, """
                            Привет! Это бот для просмотра расписания для ИСД-12.\s
                            Команды бота:\s
                            /day - расписание на сегодня.\s
                            /nextDay - расписание на следующий день.\s
                            /week - расписание на эту неделю.\s
                            /nextWeek - расписание на следующую неделю.\s
                            /custom - нахождение расписания по собственным параметрам.\s
                            /settings - изменение настроек. \s
                            """, "photo_2023-12-17_16-46-31.jpg");
                }
                case ("/day") -> {
                    String msg = dao.getTodayTable(userData.getGroup(), userData.getCourse()).toString();
                    if (msg.contains("Відпочиваємо!")) {
                        sendVideo(chatId, msg, "chill.mp4");
                    } else sendVideo(chatId, msg, "sad.mp4");
                }
                case ("/nextday") -> {
                    String msg = dao.getTomorrowTable(userData.getGroup(), userData.getCourse()).toString();
                    if (msg.contains("Відпочиваємо!")) {
                        sendVideo(chatId, msg, "chill.mp4");
                    } else sendVideo(chatId, msg, "sad.mp4");
                }
                case ("/week") -> {
                    sendPhoto(chatId, dao.getWeeklyTable(userData.getGroup(), userData.getCourse()).toString(), "photo_2023-12-17_16-46-31.jpg");
                }
                case ("/nextweek") -> {
                    sendPhoto(chatId, dao.getNextWeekTable(userData.getGroup(), userData.getCourse()).toString(), "photo_2023-12-17_16-46-31.jpg");
                }
                case ("/settings") -> {
                    sendReply(chatId, "Введіть номер групи(4 цифри) та курс(1 цифра). Наприклад: 1634 2:");
                    isWaitingForReplyMap.put(chatId, true);
                }
                default -> {
                    if (isWaitingForReplyMap.getOrDefault(chatId, false)) {
                        if (previousMessage.equalsIgnoreCase("/settings") || userData == null) {
                            userData = new UserData();
                            String[] data = receivedText.split(" ");
                            userData.setGroup(data[0]);
                            userData.setCourse(data[1]);
                            sendReply(chatId, "Дані оновлено! Ваша група: %s, ваш курс: %s".formatted(userData.getGroup(), userData.getCourse()));
                        }
                        isWaitingForReplyMap.put(chatId, false);
                    } else {
                        // Handle other messages or commands here
                        sendReply(chatId, "Команда неизвестна. Напишите /help для помощи");
                    }
                }
            }
            previousMessage = receivedText;
            execute(message);
    }

    private void sendVideo(long chatId, String message, String videoPath) {
        try {
            File video = new File(videoPath);
            SendVideo sendVideo = new SendVideo(String.valueOf(chatId), new InputFile(video));
            sendVideo.setCaption(message);
            execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendReply(long chatId, String message) {
        try {
            execute(new SendMessage(chatId+"", message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhoto(long chatId, String message, String photoPath) {
        try {
            File photo = new File(photoPath);
            SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), new InputFile(photo));
            sendPhoto.setCaption(message);
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "ІСД-12 Розклад";
    }

    @Override
    public String getBotToken() {
        return "6217671765:AAHr3WUYKZgtXrPBCY4Qi3MBAvvoJXprdNU";
    }
}