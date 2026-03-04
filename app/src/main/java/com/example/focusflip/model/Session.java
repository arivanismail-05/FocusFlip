package com.example.focusflip.model;

public class Session {

    private String subject;
    private int duration;
    private String date;

    public Session(String subject, int duration, String date) {
        this.subject = subject;
        this.duration = duration;
        this.date = date;
    }

    // ==================== GETTERS ====================

    public String getSubject() {
        return subject;
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }
}
