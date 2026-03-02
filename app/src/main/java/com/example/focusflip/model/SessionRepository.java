package com.example.focusflip.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    Context context;

    public SessionRepository(Context context) {
        this.context = context;
    }

    private JSONArray readJsonFile() {
        try {
            File file = new File(context.getFilesDir(), "sessions.json");
            if (!file.exists()) {
                return new JSONArray();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            String content = sb.toString();
            if (content.isEmpty()) {
                return new JSONArray();
            }
            return new JSONArray(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private void writeJsonFile(JSONArray array) {
        try {
            File file = new File(context.getFilesDir(), "sessions.json");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(array.toString());
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSession(Session session) {
        try {
            JSONArray oldArray = readJsonFile();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("subject", session.getSubject());
            jsonObject.put("duration", session.getDuration());
            jsonObject.put("date", session.getDate());

            JSONArray newArray = new JSONArray();
            newArray.put(jsonObject);
            for (int i = 0; i < oldArray.length(); i++) {
                newArray.put(oldArray.get(i));
            }
            writeJsonFile(newArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Session> getAllSessions() {
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            JSONArray array = readJsonFile();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String subject = obj.getString("subject");
                int duration = obj.getInt("duration");
                String date = obj.getString("date");
                sessions.add(new Session(subject, duration, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }
}

