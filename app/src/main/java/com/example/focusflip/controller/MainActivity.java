package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.model.DashboardHelper;
import com.example.focusflip.model.UserRepository;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView welcome_text, date_text, streak, total_study;
    private MaterialButton start_focus, view_history, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        displayDate();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
    }

    private void initViews() {
        welcome_text = findViewById(R.id.welcome_text);
        date_text = findViewById(R.id.date_text);
        streak = findViewById(R.id.streak);
        total_study = findViewById(R.id.total_study);
        start_focus = findViewById(R.id.start_focus);
        view_history = findViewById(R.id.view_history);
        profile = findViewById(R.id.profile);
    }

    private void displayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        date_text.setText(currentDate);
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
        streak.setText("🔥 " + helper.getStreak() + " day streak");
        total_study.setText("⏱️ " + helper.getTotalStudyTime() + " min total study time");
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
    }
}
