package com.example.focusflip.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRepository {

    private SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("register", Context.MODE_PRIVATE);
    }

    // ==================== READ ====================

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");
    }

    public boolean isRegistered() {
        return sharedPreferences.getBoolean("isRegistered", false);
    }

    // ==================== CREATE ====================

    public void register(String name, String email, String password,
                         int day, int year, String month, String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putInt("day", day);
        editor.putInt("year", year);
        editor.putString("month", month);
        editor.putString("gender", gender);
        editor.putBoolean("isRegistered", true);
        editor.apply();
    }

    // ==================== UPDATE ====================

    public void updateProfile(String name, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.apply();
    }

    // ==================== DELETE ====================

    public void deleteAccount() {
        sharedPreferences.edit().clear().apply();
    }
}
