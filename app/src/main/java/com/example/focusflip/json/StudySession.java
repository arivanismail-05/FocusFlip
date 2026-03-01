package com.example.focusflip.json;

// Simple POJO class for a study session
public class StudySession {
    private String date;
    private int durationMinutes;
    private String username;

    public StudySession(String date, int durationMinutes, String username) {
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.username = username;
    }

    public String getDate() { return date; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getUsername() { return username; }
}
