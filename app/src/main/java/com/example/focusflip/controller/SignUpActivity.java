package com.example.focusflip.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText name, email, password, confirm_password, day, year;
    RadioGroup gender_group;
    Spinner month;
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences resigter = getSharedPreferences("register", MODE_PRIVATE);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        day = findViewById(R.id.day);
        year = findViewById(R.id.year);

        gender_group = findViewById(R.id.gender_group);
        month = findViewById(R.id.month);
        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameText = name.getText().toString().trim();
                if (nameText.isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }

                String emailText = email.getText().toString().trim();
                if (emailText.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if (!emailText.contains("@") || !emailText.contains(".")) {
                    email.setError("Enter a valid email");
                    email.requestFocus();
                    return;
                }

                String passwordText = password.getText().toString();
                if (passwordText.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if (passwordText.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    password.requestFocus();
                    return;
                }

                String confirmText = confirm_password.getText().toString();
                if (confirmText.isEmpty()) {
                    confirm_password.setError("Please confirm your password");
                    confirm_password.requestFocus();
                    return;
                }
                if (!confirmText.equals(passwordText)) {
                    confirm_password.setError("Passwords do not match");
                    confirm_password.requestFocus();
                    return;
                }

                String dayText = day.getText().toString().trim();
                if (dayText.isEmpty()) {
                    day.setError("Day is required");
                    day.requestFocus();
                    return;
                }
                int day_of_birth = Integer.parseInt(dayText);
                if (day_of_birth < 1 || day_of_birth > 31) {
                    day.setError("Day must be between 1 and 31");
                    day.requestFocus();
                    return;
                }

                String yearText = year.getText().toString().trim();
                if (yearText.isEmpty()) {
                    year.setError("Year is required");
                    year.requestFocus();
                    return;
                }
                int year_of_birth = Integer.parseInt(yearText);
                if (year_of_birth < 1900 || year_of_birth > 2025) {
                    year.setError("Enter a valid year (1900-2025)");
                    year.requestFocus();
                    return;
                }

                if (gender_group.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignUpActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor edit = resigter.edit();

                edit.putString("name", nameText);
                edit.putString("password", passwordText);
                edit.putString("email", emailText);
                edit.putInt("day", day_of_birth);
                edit.putInt("year", year_of_birth);
                edit.putString("month", month.getSelectedItem().toString());
                edit.putString("gender", String.valueOf(gender_group.getCheckedRadioButtonId()));
                edit.putBoolean("isRegistered", true);
                edit.apply();

                Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}