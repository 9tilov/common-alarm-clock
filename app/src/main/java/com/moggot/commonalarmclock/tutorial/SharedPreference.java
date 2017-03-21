package com.moggot.commonalarmclock.tutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {
    private static final String s_tutorial = "tutorial";

    final static String LOG_TAG = "myLogs";

    static void SaveTutorialStatus(Context ctx, boolean status) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(s_tutorial);
        editor.putBoolean(s_tutorial, status);
        editor.apply();
    }

    public static boolean LoadTutorialStatus(Context ctx) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);

        return sharedPreferences.getBoolean(s_tutorial, true);
    }
}
