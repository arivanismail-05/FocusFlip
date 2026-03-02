package com.example.focusflip.model;

public class Session {

    String subject;
    int duration;
    String date;

    public Session(String subject, int duration, String date) {
        this.subject = subject;
        this.duration = duration;
        this.date = date;
    }

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

