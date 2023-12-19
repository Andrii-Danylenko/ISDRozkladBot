package isdrozklad.db;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GroupsDB {
    private static Map<String, String> groupsData = new HashMap<>();
    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("groupsData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsed = line.split(":");
                groupsData.put(parsed[0], parsed[1]);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public static String getInfo(String groupName) {
        return groupsData.getOrDefault(groupName, "-1");
    }

    public static String getAllGroups() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry:groupsData.entrySet()) {
            builder.append(entry.getKey()).append(',').append("\n");
        }
        return builder.substring(0, builder.length() - 2);
    }
}
