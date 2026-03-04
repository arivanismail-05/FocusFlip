package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.model.Session;
import com.example.focusflip.model.SessionRepository;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView subject_name, timer_text, status_text;
    private MaterialButton btn_start, btn_stop;

    private Handler handler = new Handler();
    private Runnable timerRunnable;
    private boolean isRunning = false;
    private int totalSeconds = 5;
    private int remainingSeconds = totalSeconds;

    private String subject;
    private SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);

        subject = getIntent().getStringExtra("subject");
        sessionRepository = new SessionRepository(this);

        initViews();
        initTimer();
        setupListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }

    private void initViews() {
        subject_name = findViewById(R.id.subject_name);
        timer_text = findViewById(R.id.timer_text);
        status_text = findViewById(R.id.status_text);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        subject_name.setText(subject);
        status_text.setText("Press Start to begin your 5-second test session");
        timer_text.setText("00:05");
    }

    private void initTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    int minutes = remainingSeconds / 60;
                    int seconds = remainingSeconds % 60;
                    timer_text.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                    handler.postDelayed(timerRunnable, 1000);
                } else {
                    onTimerComplete();
                }
            }
        };
    }

    private void setupListeners() {
        btn_start.setOnClickListener(v -> startTimer());
        btn_stop.setOnClickListener(v -> stopTimer());
    }

    private void startTimer() {
        if (!isRunning) {
            isRunning = true;
            status_text.setText("Focus session running... 🧠");
            handler.postDelayed(timerRunnable, 1000);
        }
    }

    private void stopTimer() {
        if (isRunning) {
            isRunning = false;
            handler.removeCallbacks(timerRunnable);
            status_text.setText("Session paused. Press Start to continue.");
        }
    }

    private void onTimerComplete() {
        isRunning = false;
        storeSession();
        Toast.makeText(this, "Session Complete! Well done! 🎉", Toast.LENGTH_LONG).show();
        restartActivity();
    }

    private void storeSession() {
        String today = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        Session session = new Session(subject, 25, today);
        sessionRepository.saveSession(session);
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
