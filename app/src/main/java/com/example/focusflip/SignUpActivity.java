package com.example.focusflip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText name,email,password,confirm_password,day,year;
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
                SharedPreferences.Editor edit = resigter.edit();

                int day_of_birth= Integer.parseInt(day.getText().toString());
                int year_of_birth = Integer.parseInt(year.getText().toString());

                edit.putString("name", name.getText().toString());
                edit.putString("password", password.getText().toString());
                edit.putString("email", email.getText().toString());
                edit.putInt("day" , day_of_birth);
                edit.putInt("year" , year_of_birth);
                edit.putString("month" , month.getSelectedItem().toString());
                edit.putString("gender" , String.valueOf(gender_group.getCheckedRadioButtonId()));
                edit.putBoolean("isRegistered", true);
                edit.apply();

                Intent MainActivity = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(MainActivity);
                finish();
            }
        });




    }
}