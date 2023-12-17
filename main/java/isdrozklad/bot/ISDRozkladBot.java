package isdrozklad.bot;

import isdrozklad.logic.Parser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ISDRozkladBot extends TelegramLongPollingBot {
    private Parser scheduleParser = new Parser("group-time-table.xls");

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            new Thread(() -> processUpdate(update)).start();
        }
    }

    private void processUpdate(Update update) {
        try {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);

            switch (message_text.toLowerCase()) {
                case ("/start") -> sendPhoto(chat_id, """
                        Привет! Это бот для просмотра расписания для ИСД-12.\s
                        Команды бота:\s
                        /day - расписание на сегодня.\s
                        /nextDay - расписание на следующий день.\s
                        /week - расписание на эту неделю.\s
                        /nextWeek - расписание на следующую неделю.\s
                        """, "photo_2023-12-17_16-46-31.jpg");
                case ("/day") -> {
                    String msg = scheduleParser.getTodaysTable().toString();
                    if (msg.contains("Відпочиваємо!")) {
                        sendVideo(chat_id, msg, "chill.mp4");
                    } else sendVideo(chat_id, msg, "sad.mp4");
                }
                case ("/nextday") -> {
                    String msg = scheduleParser.getTomorrowsTable().toString();
                    if (msg.contains("Відпочиваємо!")) {
                        sendVideo(chat_id, msg, "chill.mp4");
                    } else sendVideo(chat_id, msg, "sad.mp4");
                }
                case ("/week") -> sendPhoto(chat_id, scheduleParser.getThisWeekTable().toString(), "photo_2023-12-17_16-46-31.jpg");
                case ("/nextweek") -> sendPhoto(chat_id, scheduleParser.getNextWeekTable().toString(), "photo_2023-12-17_16-46-31.jpg");
                default -> message.setText("Команда неизвестна");
            }

            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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