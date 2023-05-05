package isdrozklad;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleParser {
    private int j = 3; // Отступ вниз на три ячейки, поскольку их занимает информация о группе.
    private static HSSFWorkbook workbook;

    public ScheduleParser(String path) {
        try {
            File inputFile = new File(path);
            FileInputStream stream = new FileInputStream(inputFile);
            workbook = new HSSFWorkbook(stream);
        } catch (FileNotFoundException exception) {
            System.out.println("Файл не найден!");
            exception.printStackTrace();
        } catch (IOException exception) {
            System.out.println("Ошибка при обработке запроса!");
            exception.printStackTrace();
        }
    }

    public String outputSchedule(ArrayList<Map.Entry<String, String>> schedule) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : schedule) {
            builder.append("\n").append(entry.getKey()).append(" \n").append(entry.getValue()).append("\n");
            if (entry.getKey().equalsIgnoreCase("Сб") || entry.getKey().equalsIgnoreCase("Нд")) {
                builder.append("Пар немає").append("\n");
            }
        }
        j = 3;
        return builder.toString();
    }

    private int getTodaysRow() {
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        for (int j = 0; j < lastCellNum; j++) {
            for (int i = 3; i <= lastRowNum; i++) {
                if (sheet.getRow(i) == null) continue;
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(j);
                if (cell != null) {
                    if (MyDateUtils.getCurrentDate().equals(cell.toString())) {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    public String dayScheduleLogic(int day) {
        Enum<Days> dayToday = MyDateUtils.getDayAfter(day);
        int weekRow = getTodaysRow();
        ArrayList<Map.Entry<String, String>> pair = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = weekRow; i < weekRow + 1; i++) {
            while (j < lastRowNum) {
                try {
                    Row row1 = sheet.getRow(j);
                    Cell cell1 = row1.getCell(0);
                    if (MyDateUtils.parseDay(cell1.toString()).equals(dayToday)) {
                        while (true) {
                            row1 = sheet.getRow(j);
                            cell1 = row1.getCell(0);
                            Row row = sheet.getRow(j);
                            Cell cell = row.getCell(i);
                            j++;
                            if (MyDateUtils.parseDay(cell1.toString()).equals(MyDateUtils.getDayAfter(day + 1))) {
                                return outputSchedule(pair);
                            }
                            pair.add(new HashMap.SimpleEntry<>(cell1.toString(), cell.toString()));
                        }
                    }
                } catch (NullPointerException exception) {
                    System.out.println("NPE suppressed");
                }
                j++;
            }
        }
        return null;
    }

    public String getThisDaySchedule() {
        return dayScheduleLogic(0);
    }

    public String getTomorrowSchedule() {
        return dayScheduleLogic(1);
    }

    public String getThisWeekSchedule() {
        return weekScheduleLogic(false);
    }

    public String getNextWeekSchedule() {
        return weekScheduleLogic(true);
    }

    private String weekScheduleLogic(boolean isNext) {
        int weekRow;
        if (!isNext) {
            weekRow = getTodaysRow();
        } else weekRow = getTodaysRow() + 1;
        ArrayList<Map.Entry<String, String>> pair = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = weekRow; i < weekRow + 1; i++) {
            while (j < lastRowNum) {
                if (sheet.getRow(j) != null) {
                    try {
                        Row row = sheet.getRow(j);
                        Cell cell = row.getCell(i);
                        Row row1 = sheet.getRow(j);
                        Cell cell1 = row1.getCell(0);
                        j++;
                        if (cell.toString().length() + cell1.toString().length() != 18) {
                            pair.add(new HashMap.SimpleEntry<>(cell1.toString(), cell.toString()));
                        }
                    } catch (NullPointerException exception) {
                        System.out.print("NPE caught");
                    }
                }
            }
        }
        return outputSchedule(pair);
    }
}