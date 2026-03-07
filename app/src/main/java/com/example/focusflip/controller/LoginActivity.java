package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.focusflip.R;
import com.example.focusflip.model.UserRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    private MaterialButton login;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary));
        getWindow().getDecorView().setSystemUiVisibility(0);

        userRepository = new UserRepository(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }

    private void setupListeners() {
        login.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();

        if (inputEmail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (inputPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (inputEmail.equals(userRepository.getEmail()) && inputPassword.equals(userRepository.getPassword())) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            navigateTo(MainActivity.class);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(LoginActivity.this, destination);
        startActivity(intent);
        finish();
    }
}