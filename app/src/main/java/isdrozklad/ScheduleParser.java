package isdrozklad;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleParser {
    private static File inputFile;
    private static HSSFWorkbook workbook;

    public ScheduleParser(String path) {
        try {
            inputFile = new File(path);
            FileInputStream stream = new FileInputStream(inputFile);
            workbook = new HSSFWorkbook(stream);
        } catch (FileNotFoundException exception) {
            System.out.println("‘айл не найден!");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("ќшибка при обработке запроса!");
            e.printStackTrace();
        }
    }

    public int getTodaysRow() throws IOException {
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
    public void getThisWeekSchedule() throws IOException {
        getScheduleLogic(false);
    }
    public void getNextWeekSchedule() throws IOException {
        getScheduleLogic(true);
    }
    private void getScheduleLogic(boolean isNext) throws IOException {
        int weekRow;
        if (!isNext) {
            weekRow = getTodaysRow();
        } else weekRow = getTodaysRow() + 1;
        String currentDay = MyDateUtils.getCurrentDay();
        ArrayList<Map.Entry<String, String>> pair = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = weekRow; i < weekRow+1; i++) {
            int j = 3;
            while (j < lastRowNum) {
                if (sheet.getRow(j) != null) {
                    try {
                        Row row = sheet.getRow(j);
                        Cell cell = row.getCell(i);
                        Row row1 = sheet.getRow(j);
                        Cell cell1 = row1.getCell(0);
                        j++;
                       if (cell.toString().length() + cell1.toString().length() != 18) {
                           pair.add(new HashMap.SimpleEntry<>(convertToCP1251(cell1.toString()), convertToCP1251(cell.toString())));
                       }
                    } catch (NullPointerException exception) {
                        System.out.print("NPE caught");
                    }
                }
            }
        }
       outputSchedule(pair);
    }
    public static void outputSchedule(ArrayList<Map.Entry<String, String>> schedule) {
        for (Map.Entry<String, String> entry : schedule) {
           System.out.println("\n" + entry.getKey() + " \n" + entry.getValue());
           if (entry.getKey().equalsIgnoreCase(convertToCP1251("—б")) || entry.getKey().equalsIgnoreCase(convertToCP1251("Ќд")) ) {
               System.out.println(convertToCP1251("ѕар немаЇ"));
           }
        }
    }
    public static String convertToCP1251(String input) {
        try {
            return new String(input.getBytes(StandardCharsets.UTF_8), "cp1251");
        } catch (UnsupportedEncodingException exception) {
            System.err.print("cp1251 encoding isn't supported");
            return "Goodbye...";
        }
    }
}