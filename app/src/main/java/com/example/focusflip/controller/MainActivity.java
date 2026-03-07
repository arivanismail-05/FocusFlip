package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.component.DashboardSessionAdapter;
import com.example.focusflip.model.DashboardHelper;
import com.example.focusflip.model.Session;
import com.example.focusflip.model.UserRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView welcome_text, streak, total_study, total_sessions, no_sessions_text;
    private MaterialButton start_focus;
    private LinearLayout view_history, profile;
    private ListView recent_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
        loadRecentSessions();
    }

    private void initViews() {
        welcome_text = findViewById(R.id.welcome_text);
        streak = findViewById(R.id.streak);
        total_study = findViewById(R.id.total_study);
        total_sessions = findViewById(R.id.total_sessions);
        start_focus = findViewById(R.id.start_focus);
        view_history = findViewById(R.id.view_history);
        profile = findViewById(R.id.profile);
        no_sessions_text = findViewById(R.id.no_sessions_text);
        recent_list = findViewById(R.id.recent_list);
    }

    private void setupListeners() {
        start_focus.setOnClickListener(v -> navigateTo(SelectSubjectActivity.class));
        view_history.setOnClickListener(v -> navigateTo(HistoryActivity.class));
        profile.setOnClickListener(v -> navigateTo(ProfileActivity.class));
    }

    private void loadDashboardData() {
        UserRepository userRepository = new UserRepository(this);
        welcome_text.setText("Hello, " + userRepository.getName() + "! 👋");

        DashboardHelper helper = new DashboardHelper(this);

        streak.setText(String.valueOf(helper.getStreak()));

        int todayMinutes = helper.getTodayStudyTime();
        if (todayMinutes >= 60) {
            int hours = todayMinutes / 60;
            int mins = todayMinutes % 60;
            total_study.setText(mins > 0 ? hours + "h " + mins + "m" : hours + "h");
        } else {
            total_study.setText(todayMinutes + "m");
        }

        total_sessions.setText(String.valueOf(helper.getTotalSessions()));
    }

    private void loadRecentSessions() {
        DashboardHelper helper = new DashboardHelper(this);
        List<Session> recentSessions = helper.getRecentSessions(10);

        if (recentSessions.isEmpty()) {
            no_sessions_text.setVisibility(View.VISIBLE);
            recent_list.setVisibility(View.GONE);
        } else {
            no_sessions_text.setVisibility(View.GONE);
            recent_list.setVisibility(View.VISIBLE);

            String[] subjects = new String[recentSessions.size()];
            String[] durations = new String[recentSessions.size()];

            for (int i = 0; i < recentSessions.size(); i++) {
                subjects[i] = recentSessions.get(i).getSubject();
                durations[i] = recentSessions.get(i).getDuration() + " min";
            }

            DashboardSessionAdapter adapter = new DashboardSessionAdapter(subjects, durations, this);
            recent_list.setAdapter(adapter);
        }
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
    }
}
