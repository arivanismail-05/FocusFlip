package com.example.focusflip.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.R;
import com.example.focusflip.model.UserRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    TextInputEditText profile_name, profile_email;
    MaterialButton update_profile, logout, delete_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        update_profile = findViewById(R.id.update_profile);
        logout = findViewById(R.id.logout);
        delete_account = findViewById(R.id.delete_account);

        UserRepository userRepository = new UserRepository(this);

        profile_name.setText(userRepository.getName());
        profile_email.setText(userRepository.getEmail());

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.updateProfile(
                        profile_name.getText().toString(),
                        profile_email.getText().toString()
                );
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logout();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.deleteAccount();
                Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

