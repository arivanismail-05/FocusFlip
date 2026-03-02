package com.example.focusflip;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.data.Session;
import com.example.focusflip.data.SessionRepository;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        SessionRepository sessionRepository = new SessionRepository(this);
        List<Session> sessions = sessionRepository.getAllSessions();

        if (sessions.isEmpty()) {
            Toast.makeText(this, "No sessions yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] subjects = new String[sessions.size()];
        String[] durations = new String[sessions.size()];
        String[] dates = new String[sessions.size()];

        for (int i = 0; i < sessions.size(); i++) {
            subjects[i] = sessions.get(i).getSubject();
            durations[i] = sessions.get(i).getDuration() + " min";
            dates[i] = sessions.get(i).getDate();
        }

        CustomAdapter adapter = new CustomAdapter(subjects, durations, dates, this);
        ListView history_list = findViewById(R.id.history_list);
        history_list.setAdapter(adapter);

        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(HistoryActivity.this, subjects[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}