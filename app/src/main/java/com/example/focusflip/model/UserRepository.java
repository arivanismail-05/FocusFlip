package com.example.focusflip.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRepository {

    SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("register", Context.MODE_PRIVATE);
    }

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

    public void updateName(String name) {
        sharedPreferences.edit().putString("name", name).apply();
    }

    public void updateEmail(String email) {
        sharedPreferences.edit().putString("email", email).apply();
    }

    public void updateProfile(String name, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.apply();
    }

    public void logout() {
        sharedPreferences.edit().putBoolean("isRegistered", false).apply();
    }

    public void deleteAccount() {
        sharedPreferences.edit().clear().apply();
    }
}

