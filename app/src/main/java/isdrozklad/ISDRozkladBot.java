package isdrozklad;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ISDRozkladBot extends TelegramLongPollingBot {
    private static ScheduleParser scheduleParser = new ScheduleParser("C:\\Users\\Ineed\\Downloads\\group-time-table.xls");
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            message.setText(message_text);
            switch (message_text.toLowerCase()) {
                case ("/start"): message.setText("""
                        Привет! Это бот для просмотра расписания для ИСД-12.\s
                        Команды бота:\s
                        /day - расписание на сегодня.\s
                        /nextDay - расписание на следующий день.\s
                        /week - расписание на эту неделю.\s
                        /nextWeek - расписание на следующую неделю.\s
                        """);
                break;
                case ("/day"): message.setText(scheduleParser.getThisDaySchedule());
                break;
                case ("/nextday"): message.setText(scheduleParser.getTomorrowSchedule());
                break;
                case ("/week"): message.setText(scheduleParser.getThisWeekSchedule());
                break;
                case ("/nextweek"): message.setText(scheduleParser.getNextWeekSchedule());
                break;
                default: message.setText("Команда неизвестна");
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
