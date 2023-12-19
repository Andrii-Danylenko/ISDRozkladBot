package isdrozklad.db;

import isdrozklad.entities.UserData;

import java.util.HashMap;

public class UserDB {
    private static HashMap<Long, UserData> userData = new HashMap<>();

    public static void addData(long chatId, UserData data) {
        userData.put(chatId, data);
    }
    public static void changeData(long chatId, UserData data) {
        userData.computeIfPresent(chatId, (k, v) -> data);
    }
    public static UserData getData(long chatId) {
        return userData.get(chatId);
    }
}
