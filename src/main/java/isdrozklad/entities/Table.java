package isdrozklad.entities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<DayOfWeek> dayOfWeeks;
    public Table() {
        dayOfWeeks = new ArrayList<>();
    }
    public List<DayOfWeek> getTable() {
        return dayOfWeeks;
    }
    public void setDayOfWeeks(List<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        dayOfWeeks.forEach(x -> builder.append(x).append("\n"));
        return builder.toString();
    }
}
