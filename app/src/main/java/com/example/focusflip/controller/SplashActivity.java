package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.model.UserRepository;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        UserRepository userRepository = new UserRepository(this);

        // User already signed up before → go straight to Login, never show Splash again
        if (userRepository.isRegistered()) {
            navigateTo(LoginActivity.class);
            return;
        }

        // First time ever → show Splash, tap to go to SignUp
        setupListeners();
    }

    private void setupListeners() {
        LinearLayout main = findViewById(R.id.main);
        main.setOnClickListener(v -> navigateTo(SignUpActivity.class));
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(SplashActivity.this, destination);
        startActivity(intent);
        finish();
    }
}
