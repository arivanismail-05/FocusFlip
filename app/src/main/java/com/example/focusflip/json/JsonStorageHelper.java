package com.example.focusflip.json;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Handles saving and retrieving sessions using JsonFileManager
public class JsonStorageHelper {

    // Save a single session
    public static void saveSession(Context context, StudySession session) {
        try {
            String json = JsonFileManager.readFromFile(context);
            JSONArray array = new JSONArray(json);

            JSONObject obj = new JSONObject();
            obj.put("date", session.getDate());
            obj.put("durationMinutes", session.getDurationMinutes());
            obj.put("username", session.getUsername());

            array.put(obj);
            JsonFileManager.writeToFile(context, array.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all sessions
    public static List<StudySession> getAllSessions(Context context) {
        List<StudySession> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(JsonFileManager.readFromFile(context));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                list.add(new StudySession(
                        obj.getString("date"),
                        obj.getInt("durationMinutes"),
                        obj.getString("username")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get total minutes studied today
    public static int getTodayMinutes(Context context) {
        String today = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        int total = 0;
        for (StudySession s : getAllSessions(context)) {
            if (s.getDate().equals(today)) total += s.getDurationMinutes();
        }
        return total;
    }

    // Get number of sessions today
    public static int getTodaySessionsCount(Context context) {
        String today = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        int count = 0;
        for (StudySession s : getAllSessions(context)) {
            if (s.getDate().equals(today)) count++;
        }
        return count;
    }
}
