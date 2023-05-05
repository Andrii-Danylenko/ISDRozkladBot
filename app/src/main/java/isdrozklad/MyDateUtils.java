package isdrozklad;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateUtils {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("E");
    private static final LocalDateTime date = LocalDateTime.now();

    public static Enum<Days> getDayAfter(int days) {
        return parseDay(dayFormat.format(date.plusDays(days)));
    }

    public static String getCurrentDate() {
        return format.format(date);
    }

    /*
       Этот кусок кода является надругательством над всем святым в этом мире.
       Поскольку никакие методы сравнения не работают из-за сломанной кодировки на моей Intellij, то я отчаялся
       И начал сравнивать байты.
       Сильно не бейте
    */
    public static Enum<Days> parseDay(String input) {
        switch (input.toLowerCase()) {
            case ("пн"):
                return Days.MONDAY;
            case ("вт"):
                return Days.TUESDAY;
            case ("ср"):
                return Days.WEDNESDAY;
            case ("чт"):
                return Days.THURSDAY;
            case ("пт"):
                return Days.FRIDAY;
            case ("сб"):
                return Days.SATURDAY;
            case ("нд"):
                return Days.SUNDAY;
            default:
                return Days.INVALIDDAY;
        }
    }
}
