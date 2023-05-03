package isdrozklad;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MyDateUtils {
   private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("E");
   private static final LocalDateTime date = LocalDateTime.now();

    public static String getCurrentDay() {
        return ScheduleParser.convertToCP1251(dayFormat.format(date));
   }
    public static String getDayAfter() {
        return ScheduleParser.convertToCP1251(dayFormat.format(date.plusDays(1)));
    }
   public static String getCurrentDate() {
       return ScheduleParser.convertToCP1251(format.format(date));
   }
}
