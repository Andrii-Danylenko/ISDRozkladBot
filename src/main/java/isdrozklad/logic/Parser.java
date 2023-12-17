package isdrozklad.logic;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import isdrozklad.entities.Classes;
import isdrozklad.entities.DayOfWeek;
import isdrozklad.entities.Table;
import isdrozklad.utils.DateUtils;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private Workbook workbook;

    public Parser(String pathToFile) {
        try (FileInputStream wb = new FileInputStream(pathToFile)) {
            workbook = new HSSFWorkbook(wb);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Table getThisWeekTable() {
        return parseXLS(DateUtils.getTodayDateString());
    }

    public Table getNextWeekTable() {
        return parseXLS(DateUtils.toString(LocalDate.now().plusDays(7)));
    }

    public Table getTomorrowsTable() {
        LocalDate date = LocalDate.now().plusDays(1);
        return getNextDayTable(parseXLS(DateUtils.toString(date)), date);
    }
    public Table getTodaysTable() {
        LocalDate date = LocalDate.now();
        return getNextDayTable(parseXLS(DateUtils.toString(date)), date);
    }
    private Table parseXLS(String date) {
        Sheet sheet = workbook.getSheetAt(0);
        Table table = new Table();
        DayOfWeek day;
        int[] offset = this.getOffset(date);
        List<DayOfWeek> dayOfWeeks = new ArrayList<>();
        for (int i = 3; i < sheet.getLastRowNum(); ) {
            if (DateUtils.isDayOfWeek(sheet.getRow(i).getCell(0).toString())) {
                day = new DayOfWeek();
                day.setDate(DateUtils.parseDate(sheet.getRow(i).getCell(offset[1]).toString()));
                day.setDayOfWeek(DateUtils.getFullName(sheet.getRow(i++).getCell(0).toString()));
                while (i <= sheet.getLastRowNum() && !DateUtils.isDayOfWeek(sheet.getRow(i).getCell(0).toString())) {
                    Classes clazz = new Classes();
                    String[] data = sheet.getRow(i).getCell(offset[1]).toString().split("\n");
                    if (data.length == 3) {
                        clazz.setPairNumber(sheet.getRow(i).getCell(0).toString().charAt(0) - '0');
                        clazz.setPairTime(sheet.getRow(i).getCell(0).toString().split("\n")[1]);
                    }
                    clazz.setPairDetails(sheet.getRow(i).getCell(offset[1]).toString().replaceAll("\n", ", "));
                    day.getPairsList().add(clazz);
                    i++;
                }
                dayOfWeeks.add(day);
            }
        }
        table.setDayOfWeeks(dayOfWeeks);
        return table;
    }

    private Table getNextDayTable(Table weekTable, LocalDate dayDate) {
        Table table = new Table();
        for (DayOfWeek day:weekTable.getTable()) {
            if (day.getDate().equals(dayDate)) {
                table.getTable().add(day);
                break;
            }
        }
        return table;
    }
    public int[] getOffset(String date) {
        Sheet sheet = workbook.getSheetAt(0);
        String str;
        for (int i = 3; i < sheet.getLastRowNum(); i++) {
            if (DateUtils.isDayOfWeek(sheet.getRow(i).getCell(0).toString())) {
                int j = 0;
                while (j < sheet.getLastRowNum()) {
                    str = String.valueOf(sheet.getRow(i).getCell(j));
                    if (str != null && str.equals(date)) {
                        return new int[]{i, j};
                    }
                    j++;
                }
            }
        }
        return new int[]{0, 0};
    }
}
