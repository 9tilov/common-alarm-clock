package com.moggot.commonalarmclock.domain.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.domain.schedule.RestoreAlarmsAfterRebootService;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.BuzzerActivity;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long id = extras.getLong(Consts.EXTRA_ID);
        if (id == Consts.NO_ID) {
            context.startService(new Intent(context, RestoreAlarmsAfterRebootService.class));
            return;
        }
        intent = new Intent(context, BuzzerActivity.class);
        intent.putExtra(Consts.EXTRA_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}