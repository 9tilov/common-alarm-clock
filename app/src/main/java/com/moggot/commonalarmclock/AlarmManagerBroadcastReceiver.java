package com.moggot.commonalarmclock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "AlarmManagerBroadcast";

    @SuppressLint("InlinedApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG, "alarm");
    }
}