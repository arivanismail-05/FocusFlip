package com.example.focusflip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        boolean isRegistered = getSharedPreferences("register", MODE_PRIVATE).getBoolean("isRegistered",false);

        if(isRegistered){
            Intent LoginActivity = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(LoginActivity);
            finish();
        }

        LinearLayout main = findViewById(R.id.main);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUpActivity = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(SignUpActivity);
                finish();
            }
        });
    }
}