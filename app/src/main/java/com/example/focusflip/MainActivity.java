package com.example.focusflip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.data.DashboardHelper;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView welcome_text,date_text,streak,total_study;
    MaterialButton start_focus,view_history,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        welcome_text = findViewById(R.id.welcome_text);
        date_text = findViewById(R.id.date_text);
        streak = findViewById(R.id.streak);
        total_study = findViewById(R.id.total_study);

        start_focus = findViewById(R.id.start_focus);
        view_history = findViewById(R.id.view_history);
        profile = findViewById(R.id.profile);

        String name = getSharedPreferences("register", MODE_PRIVATE).getString("name","");

        welcome_text.setText("Hello, "+name+"! 👋");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        date_text.setText(currentDate);

        start_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent focusActivity = new Intent(MainActivity.this, SelectSubjectActivity.class);
                startActivity(focusActivity);
            }
        });

        view_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent focusActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(focusActivity);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent focusActivity = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(focusActivity);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload name from SharedPreferences so it updates after profile edit
        String name = getSharedPreferences("register", MODE_PRIVATE).getString("name", "");
        welcome_text.setText("Hello, " + name + "! 👋");

        // Use DashboardHelper to calculate streak and total study time
        DashboardHelper helper = new DashboardHelper(this);

        int streakCount = helper.getStreak();
        streak.setText("🔥 " + streakCount + " day streak");

        int totalMinutes = helper.getTotalStudyTime();
        total_study.setText("⏱️ " + totalMinutes + " min total study time");
    }
}