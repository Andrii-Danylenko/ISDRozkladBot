package isdrozklad;
public class App {

    public static void main(String[] args) {
        ScheduleParser scheduleParser = new ScheduleParser("C:\\Users\\Ineed\\Downloads\\group-time-table.xls");
        System.out.println(scheduleParser.getTomorrowSchedule());
    }
}
