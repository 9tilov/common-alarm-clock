package com.moggot.commonalarmclock.schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.alarm.Alarm;

import javax.inject.Inject;

public class SnoozeAlarm implements AlarmStrategy {

    private int requestCode;
    private long currentTime;

    @Inject
    Intent intent;

    @Inject
    AlarmManager alarmManager;

    @Inject
    Context context;

    public SnoozeAlarm(int requestCode, long currentTime) {
        this.requestCode = requestCode;
        this.currentTime = currentTime;

    }

    public void setAlarm(Alarm alarm) {
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        currentTime += Consts.SNOOZE_TIME_IN_MINUTES * 60000;
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime, pi);
    }

    @Override
    public void cancelAlarm(Alarm alarm) {

    }
}
