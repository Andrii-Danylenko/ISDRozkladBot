package isdrozklad.logic;

import isdrozklad.entities.Table;
import isdrozklad.utils.DateUtils;

import java.time.LocalDate;

public class DAOImpl implements DAO {
    private JSONParser parser = new JSONParser();

    @Override
    public Table getWeeklyTable(String group, String course) {
        LocalDate startOfWeek = DateUtils.getStartOfWeek(DateUtils.getTodayDateString());
        return parser.table(group, DateUtils.toString(startOfWeek), DateUtils.toString(startOfWeek.plusDays(7)), course);
    }

    @Override
    public Table getNextWeekTable(String group, String course) {
        LocalDate startOfWeek = DateUtils.getStartOfWeek(DateUtils.getTodayDateString());
        return parser.table(group, DateUtils.toString(startOfWeek.plusDays(7)), DateUtils.toString(startOfWeek.plusDays(14)), course);
    }

    @Override
    public Table getTodayTable(String group, String course) {
        return parser.table(group, DateUtils.getTodayDateString(), DateUtils.getTodayDateString(), course);
    }

    @Override
    public Table getTomorrowTable(String group, String course) {
        return parser.table(group, DateUtils.toString(DateUtils.getTodayDate().plusDays(1)), DateUtils.toString(DateUtils.getTodayDate().plusDays(1)), course);
    }

    @Override
    public Table getCustomTable(String group, String dateFrom, String dateTo, String course) {
        if (DateUtils.parseDate(dateTo).isBefore(DateUtils.parseDate(dateFrom))) {
            dateTo = DateUtils.toString(DateUtils.getTodayDate().plusDays(1));
        }
        return parser.table(group, dateFrom, dateTo, course);
    }
}
