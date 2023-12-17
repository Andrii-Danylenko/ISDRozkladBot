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
          builder.append("%s - %s".formatted(dayOfWeek, date)).append("\n");
          for (Classes clazz: pairsList) {
               if (!clazz.getPairDetails().isEmpty()) {
                    hasPairs = true;
                    builder.append("%d. %s - %s\n".formatted(clazz.getPairNumber(), clazz.getPairTime(), clazz.getPairDetails()));
               }
          }
          return hasPairs ? builder.toString() : builder.append("Пар немає! Відпочиваємо!\n").toString();
     }
}
