package com.moggot.commonalarmclock;

import android.app.*;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 13.03.17.
 */

public class SnoozeAlarm {

    private Context context;

    public SnoozeAlarm(Context context) {
        this.context = context;
    }

    public void setAlarm(Alarm alarm, long time) {
        DataBase db = new DataBase(context);
        int requestCode = db.getRandomRequestCode();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        time += 60000;
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
    }
}
