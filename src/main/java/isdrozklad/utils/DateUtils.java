package isdrozklad.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {
    private static final Locale locale = new Locale("uk");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(locale);

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
        public static String getDayOfWeek(String date) {
            String dayOfWeek = LocalDate.parse(date, formatter).getDayOfWeek().getDisplayName(TextStyle.FULL,locale);
            return dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        }
    public static LocalDate getStartOfWeek(String date) {
        LocalDate inputDate = LocalDate.parse(date, formatter);
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        int daysUntilStartOfWeek = DayOfWeek.from(inputDate).compareTo(firstDayOfWeek);

        return inputDate.minusDays(daysUntilStartOfWeek);
    }
    public static String getPairTime(String pairNumber) {
        switch (pairNumber) {
            case "1" -> {
                return "8:00-9:35";
            }
            case "2" -> {
                return "9:45-11:20";
            }
            case "3" -> {
                return "11:45-13:20";
            }
            case "4" -> {
                return "13:30-15:05";
            }
            case "5" -> {
                return "15:15-16:50";
            }
            case "6" -> {
                return "17:00-18:35";
            }
            default -> {
                return "18:45-20:15";
            }
        }
    }
}
