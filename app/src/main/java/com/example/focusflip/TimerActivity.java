package com.example.focusflip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.data.Session;
import com.example.focusflip.data.SessionRepository;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    TextView subject_name;
    TextView timer_text;
    TextView status_text;
    MaterialButton btn_start;
    MaterialButton btn_stop;
    Handler handler = new Handler();
    Runnable timerRunnable;
    boolean isRunning = false;
    int totalSeconds = 5;
    int remainingSeconds = totalSeconds;
    String subject;
    SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);

        subject = getIntent().getStringExtra("subject");
        sessionRepository = new SessionRepository(this);

        subject_name = findViewById(R.id.subject_name);
        timer_text = findViewById(R.id.timer_text);
        status_text = findViewById(R.id.status_text);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        subject_name.setText(subject);
        status_text.setText("Press Start to begin your 5-second test session");
        timer_text.setText("00:05");

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
                    isRunning = false;
                    String today = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
                    Session session = new Session(subject, 25, today);
                    sessionRepository.saveSession(session);
                    Toast.makeText(TimerActivity.this, "Session Complete! Well done! 🎉", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        };

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    status_text.setText("Focus session running... 🧠");
                    handler.postDelayed(timerRunnable, 1000);
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    handler.removeCallbacks(timerRunnable);
                    status_text.setText("Session paused. Press Start to continue.");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }
}