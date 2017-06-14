package com.moggot.commonalarmclock.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {

    private static final String s_tutorial = "tutorial";

    private Context context;

    public SharedPreference(Context context) {
        this.context = context;
    }

    public void saveTutorialStatus(boolean status) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(s_tutorial);
        editor.putBoolean(s_tutorial, status);
        editor.apply();
    }

    public boolean loadTutorialStatus() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean(s_tutorial, true);
    }
}
