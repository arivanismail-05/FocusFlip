package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.model.UserRepository;
import com.example.focusflip.model.SessionRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText profile_name, profile_email;
    private MaterialButton update_profile, logout, delete_account;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        userRepository = new UserRepository(this);
        sessionRepository = new SessionRepository(this);

        initViews();
        loadProfile();
        setupListeners();
    }

    private void initViews() {
        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        update_profile = findViewById(R.id.update_profile);
        logout = findViewById(R.id.logout);
        delete_account = findViewById(R.id.delete_account);
    }

    private void loadProfile() {
        profile_name.setText(userRepository.getName());
        profile_email.setText(userRepository.getEmail());
    }

    private void setupListeners() {
        update_profile.setOnClickListener(v -> updateProfile());
        logout.setOnClickListener(v -> performLogout());
        delete_account.setOnClickListener(v -> performDeleteAccount());
    }

    private void updateProfile() {
        userRepository.updateProfile(
                profile_name.getText().toString(),
                profile_email.getText().toString()
        );
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
    }

    private void performLogout() {
        navigateTo(LoginActivity.class);
    }

    private void performDeleteAccount() {
        userRepository.deleteAccount();
        sessionRepository.deleteAllSessions();
        navigateTo(SplashActivity.class);
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(ProfileActivity.this, destination);
        startActivity(intent);
        finish();
    }
}

