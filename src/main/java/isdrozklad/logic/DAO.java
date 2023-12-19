package isdrozklad.logic;

import isdrozklad.entities.Table;

public interface DAO {
    Table getWeeklyTable(String group, String course);

    Table getNextWeekTable(String group, String course);

    Table getTodayTable(String group, String course);

    Table getTomorrowTable(String group, String course);

    Table getCustomTable(String group, String dateFrom, String dateTo, String course);
}
