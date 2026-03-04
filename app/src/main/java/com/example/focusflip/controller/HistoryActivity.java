package com.example.focusflip.controller;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.component.CustomAdapter;
import com.example.focusflip.model.Session;
import com.example.focusflip.model.SessionRepository;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView history_list;
    private SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        sessionRepository = new SessionRepository(this);

        initViews();
        loadHistory();
    }

    private void initViews() {
        history_list = findViewById(R.id.history_list);
    }

    private void loadHistory() {
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
        history_list.setAdapter(adapter);

        setupListeners(subjects);
    }

    private void setupListeners(String[] subjects) {
        history_list.setOnItemClickListener((parent, view, i, id) ->
                Toast.makeText(this, subjects[i], Toast.LENGTH_SHORT).show()
        );
    }
}
