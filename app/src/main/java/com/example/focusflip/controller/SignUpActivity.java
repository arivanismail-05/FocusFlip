package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.focusflip.R;
import com.example.focusflip.model.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText name, email, password, confirm_password, day, year;
    private RadioGroup gender_group;
    private Spinner month;
    private Button sign_up;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary));
        getWindow().getDecorView().setSystemUiVisibility(0);

        userRepository = new UserRepository(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        day = findViewById(R.id.day);
        year = findViewById(R.id.year);
        gender_group = findViewById(R.id.gender_group);
        month = findViewById(R.id.month);
        sign_up = findViewById(R.id.sign_up);
    }

    private void setupListeners() {
        sign_up.setOnClickListener(v -> attemptSignUp());
    }

    private void attemptSignUp() {
        if (!validateInputs()) {
            return;
        }

        storeUser();

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        navigateTo(MainActivity.class);
    }

    private boolean validateInputs() {
        String nameText = name.getText().toString().trim();
        if (nameText.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return false;
        }

        String emailText = email.getText().toString().trim();
        if (emailText.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }
        if (!emailText.contains("@") || !emailText.contains(".")) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return false;
        }

        String passwordText = password.getText().toString();
        if (passwordText.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }
        if (passwordText.length() < 6) {
            password.setError("Password must be at least 6 characters");
            password.requestFocus();
            return false;
        }

        String confirmText = confirm_password.getText().toString();
        if (confirmText.isEmpty()) {
            confirm_password.setError("Please confirm your password");
            confirm_password.requestFocus();
            return false;
        }
        if (!confirmText.equals(passwordText)) {
            confirm_password.setError("Passwords do not match");
            confirm_password.requestFocus();
            return false;
        }

        String dayText = day.getText().toString().trim();
        if (dayText.isEmpty()) {
            day.setError("Day is required");
            day.requestFocus();
            return false;
        }
        int day_of_birth = Integer.parseInt(dayText);
        if (day_of_birth < 1 || day_of_birth > 31) {
            day.setError("Day must be between 1 and 31");
            day.requestFocus();
            return false;
        }

        String yearText = year.getText().toString().trim();
        if (yearText.isEmpty()) {
            year.setError("Year is required");
            year.requestFocus();
            return false;
        }
        int year_of_birth = Integer.parseInt(yearText);
        if (year_of_birth < 1900 || year_of_birth > 2025) {
            year.setError("Enter a valid year (1900-2025)");
            year.requestFocus();
            return false;
        }

        if (gender_group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void storeUser() {
        userRepository.register(
                name.getText().toString().trim(),
                email.getText().toString().trim(),
                password.getText().toString(),
                Integer.parseInt(day.getText().toString().trim()),
                Integer.parseInt(year.getText().toString().trim()),
                month.getSelectedItem().toString(),
                String.valueOf(gender_group.getCheckedRadioButtonId())
        );
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(SignUpActivity.this, destination);
        startActivity(intent);
        finish();
    }
}