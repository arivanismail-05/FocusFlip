package com.example.focusflip.model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DashboardHelper {

    SessionRepository sessionRepository;

    public DashboardHelper(Context context) {
        sessionRepository = new SessionRepository(context);
    }

    public int getTotalStudyTime() {
        List<Session> sessions = sessionRepository.getAllSessions();
        int total = 0;
        for (int i = 0; i < sessions.size(); i++) {
            total += sessions.get(i).getDuration();
        }
        return total;
    }

    public int getStreak() {
        List<Session> sessions = sessionRepository.getAllSessions();
        if (sessions.isEmpty()) {
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Set<String> sessionDates = new HashSet<>();
        for (int i = 0; i < sessions.size(); i++) {
            sessionDates.add(sessions.get(i).getDate());
        }

        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());

        int streak = 0;

        if (sessionDates.contains(today)) {
            streak = 1;
            cal.add(Calendar.DAY_OF_YEAR, -1);
            while (sessionDates.contains(sdf.format(cal.getTime()))) {
                streak++;
                cal.add(Calendar.DAY_OF_YEAR, -1);
            }
        } else {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            if (sessionDates.contains(sdf.format(cal.getTime()))) {
                streak = 1;
                cal.add(Calendar.DAY_OF_YEAR, -1);
                while (sessionDates.contains(sdf.format(cal.getTime()))) {
                    streak++;
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                }
            }
        }

        return streak;
    }
}

