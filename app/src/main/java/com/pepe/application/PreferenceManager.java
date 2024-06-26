package com.pepe.application;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREFERENCES_NAME = "app_preferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role"; // Nuevo

    private SharedPreferences preferences;
    private static PreferenceManager instance;

    private PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context);
        }
        return instance;
    }

    public void setUsername(String username) {
        preferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, null);
    }

    public void setEmail(String email) {
        preferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    // Nuevo: Agregar métodos para gestionar el rol
    public void setRole(String role) {
        preferences.edit().putString(KEY_ROLE, role).apply();
    }

    public String getRole() {
        return preferences.getString(KEY_ROLE, null);
    }
}
