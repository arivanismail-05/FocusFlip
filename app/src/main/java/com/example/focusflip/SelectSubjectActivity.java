package com.example.focusflip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SelectSubjectActivity extends AppCompatActivity {

    ListView subject_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_subject);

        subject_list = findViewById(R.id.subject_list);

        String[] subjects = {"Mathematics", "Physics", "Chemistry",
                "Biology", "Computer Science", "English", "History"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjects);
        subject_list.setAdapter(adapter);

        subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(SelectSubjectActivity.this, TimerActivity.class);
                intent.putExtra("subject", subjects[i]);
                startActivity(intent);
            }
        });
    }
}