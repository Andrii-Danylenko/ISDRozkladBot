package isdrozklad.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayOfWeek {
     LocalDate date;
     String dayOfWeek;
     List<Classes> pairsList;

     public DayOfWeek(LocalDate date, String dayOfWeek, List<Classes> pairsList) {
          this.date = date;
          this.dayOfWeek = dayOfWeek;
          this.pairsList = pairsList;
     }

     public DayOfWeek() {
          pairsList = new ArrayList<>();
     }

     public LocalDate getDate() {
          return date;
     }

     public void setDate(LocalDate date) {
          this.date = date;
     }

     public String getDayOfWeek() {
          return dayOfWeek;
     }

     public void setDayOfWeek(String dayOfWeek) {
          this.dayOfWeek = dayOfWeek;
     }

     public List<Classes> getPairsList() {
          return pairsList;
     }

     public void setPairsList(List<Classes> pairsList) {
          this.pairsList = pairsList;
     }

     @Override
     public String toString() {
          boolean hasPairs = false;
          StringBuilder builder = new StringBuilder();
          builder.append("%s - %s %s".formatted(dayOfWeek, date, DayOfWeek.getAmountOfPairs(this))).append("\n");
          for (Classes clazz: pairsList) {
               if (!clazz.getPairDetails().isEmpty()) {
                    hasPairs = true;
                    builder.append("%d. %s - %s\n".formatted(clazz.getPairNumber(), clazz.getPairTime(), clazz.getPairDetails()));
               }
          }
          return hasPairs ? builder.toString() : builder.append("Пар немає! Відпочиваємо!\n").toString();
     }

     public static String getAmountOfPairs(DayOfWeek day) {
          switch (day.pairsList.size()) {
               case 1: return "(1 пара)";
               case 2: return "(2 пари)";
               case 3: return "(3 пари)";
               case 4: return "(4 пари)";
               case 5: return "(5 пар)";
               case 6: return "(6 пар)";
               default: return "(пар немає)";
          }
     }
}
