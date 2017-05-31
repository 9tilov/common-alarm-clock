package com.moggot.commonalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = AlarmManagerBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long id = extras.getLong(Consts.EXTRA_ID);
        if (id == Consts.NO_ID) {
            context.startService(new Intent(context, RestoreAlarmsAfterRebootService.class));
            return;
        }
        intent = new Intent(context, ActivityGetUpAlarm.class);
        intent.putExtra(Consts.EXTRA_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}