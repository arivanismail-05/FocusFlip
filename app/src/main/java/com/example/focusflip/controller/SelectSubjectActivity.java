package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;

public class SelectSubjectActivity extends AppCompatActivity {

    private ListView subject_list;

    private final String[] subjects = {
            "Mathematics", "Physics", "Chemistry",
            "Biology", "Computer Science", "English", "History"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_subject);

        initViews();
        loadSubjects();
        setupListeners();
    }

    private void initViews() {
        subject_list = findViewById(R.id.subject_list);
    }

    private void loadSubjects() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjects);
        subject_list.setAdapter(adapter);
    }

    private void setupListeners() {
        subject_list.setOnItemClickListener((parent, view, i, id) -> {
            Intent intent = new Intent(SelectSubjectActivity.this, TimerActivity.class);
            intent.putExtra("subject", subjects[i]);
            startActivity(intent);
        });
    }
}
