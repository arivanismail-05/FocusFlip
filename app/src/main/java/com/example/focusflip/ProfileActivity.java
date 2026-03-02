package com.example.focusflip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

        String name = getSharedPreferences("register", MODE_PRIVATE).getString("name", "");
        String email = getSharedPreferences("register", MODE_PRIVATE).getString("email", "");

        profile_name.setText(name);
        profile_email.setText(email);

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("register", MODE_PRIVATE).edit();
                editor.putString("name", profile_name.getText().toString());
                editor.putString("email", profile_email.getText().toString());
                editor.apply();
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("register", MODE_PRIVATE).edit();
                editor.putBoolean("isRegistered", false);
                editor.apply();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("register", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}