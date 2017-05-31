package com.moggot.commonalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;

public class SingleAlarmStrategy implements Strategy {

    private Context context;

    public SingleAlarmStrategy(Context context) {
        this.context = context;
    }

    public void setAlarm(Alarm alarm) {
        long alarmPeriod = alarm.getTimeInMillis();
        Calendar calendar = Calendar.getInstance();
        while (alarmPeriod < calendar.getTimeInMillis())
            alarmPeriod += AlarmManager.INTERVAL_DAY;
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent pi = PendingIntent.getBroadcast(context, alarm.getRepeatAlarmIDs().valueAt(0), intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, alarmPeriod, pi);
    }

    public void cancelAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent sender = PendingIntent.getBroadcast(context, alarm.getRepeatAlarmIDs().valueAt(0),
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
