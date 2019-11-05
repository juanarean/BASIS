package com.basis.basis.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.basis.basis.R;

public class SharedPreferencesManager {

    public SharedPreferencesManager() {

    }

    private static SharedPreferences getSharedPreferences() {
        return MyApp.getContext().getSharedPreferences(Constantes.SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static void setStringValue(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.apply();
    }

    public static void setBooleanValue(String dataLabel, Boolean dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.apply();
    }

    public static String getStringValue(String dataLabel) {
        return getSharedPreferences().getString(dataLabel, null);
    }

    public static Boolean getBooleanValue(String dataLabel) {
        return getSharedPreferences().getBoolean(dataLabel, false);
    }
}
