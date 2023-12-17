package isdrozklad.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static boolean isDayOfWeek(String info) {
        return switch (info.toLowerCase()) {
            case "пн", "вт", "ср", "чт", "пт", "сб", "нд" -> true;
            default -> false;
        };
    }
    public static String getFullName(String name) {
        return switch (name.toLowerCase()) {
            case "пн" -> "Понеділок";
            case "вт" -> "Вівторок";
            case "ср" -> "Середа";
            case "чт" -> "Четвер";
            case "пт" -> "П'ятниця";
            case "сб" -> "Субота";
            case "нд" -> "Неділя";
            default -> "Undefined";
        };
    }
    public static LocalDate getTodayDate() {
        return LocalDate.parse(LocalDateTime.now().format(formatter), formatter);
    }
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(LocalDate.parse(date, formatter).format(formatter), formatter);
    }
    public static LocalDate addDays(LocalDate date, int daysToAdd) {
        return date.plusDays(daysToAdd);
    }
    public static String getTodayDateString() {
        return LocalDate.now().format(formatter);
    }
    public static String toString(LocalDate date) {
        return date.format(formatter);
    }
}
