package isdrozklad.logic;

import isdrozklad.entities.Classes;
import isdrozklad.entities.DayOfWeek;
import isdrozklad.entities.Table;
import isdrozklad.utils.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class JSONParser {

    private Iterator<JSONObject> getJsonObject(String group, String dateFrom, String dateTo, String course) throws IOException, ParseException {
        String strUrl = "https://skedy.api.yacode.dev/v1/student/schedule?faculty=1&group=%s&dateFrom=%s&dateTo=%s&course=%s".formatted(group, dateFrom, dateTo, course);
        System.out.println(strUrl);
        URL url = new URL(strUrl);
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(url.openStream()));
        JSONArray jsonArray = (JSONArray) jsonObject.get("schedule");
        return jsonArray.iterator();
    }

    private Table parseData(Iterator<JSONObject> dataIterator, String startDate, String endDate) {
        TreeMap<LocalDate, List<Classes>> map = new TreeMap<>();
        while (dataIterator.hasNext()) {
            JSONObject obj = dataIterator.next();
            LocalDate date = DateUtils.parseDate(String.valueOf(obj.get("date")));
            int pairNumber = Integer.parseInt((String) obj.get("number"));
            String pairDetails = "[%s], каб. %s, %s".formatted(obj.get("type"), obj.get("cabinet"), obj.get("whoShort"));
            if (!map.containsKey(date)) {
                List<Classes> list = new ArrayList<>();
                list.add(new Classes(pairNumber, DateUtils.getPairTime(pairNumber + ""), pairDetails));
                map.put(date, list);
            } else {
                map.get(date).add(new Classes(pairNumber, DateUtils.getPairTime(pairNumber + ""), pairDetails));
            }
        }
        validateData(map, startDate, endDate);
        return getTable(map);
    }
    private Table getTable(TreeMap<LocalDate, List<Classes>> data) {
        Table table = new Table();
        for (Map.Entry<LocalDate, List<Classes>> entry:data.entrySet()) {
            DayOfWeek day = new DayOfWeek(entry.getKey(), DateUtils.getDayOfWeek(DateUtils.toString(entry.getKey())), entry.getValue());
            table.getTable().add(day);
        }
        return table;
    }
    private void validateData(TreeMap<LocalDate, List<Classes>> data, String startDate, String endDate) {
        LocalDate startD = DateUtils.parseDate(startDate);
        LocalDate endD = DateUtils.parseDate(endDate);
        while (startD.isBefore(endD)) {
            if (!data.containsKey(startD)) {
                data.put(startD, new ArrayList<>());
            }
            startD = startD.plusDays(1);
        }
    }
    public Table table(String group, String dateFrom, String dateTo, String course) {
        Table table = new Table();
        try {
            table = parseData(getJsonObject(group, dateFrom, dateTo, course), dateFrom, dateTo);
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
        return table;
    }
}
