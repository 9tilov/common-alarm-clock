package com.moggot.commonalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;

/**
 * Created by toor on 28.02.17.
 */

public class SingleAlarm implements AlarmType {

    public void setAlarm(AlarmContext alarmContext) {
        Alarm alarm = alarmContext.getAlarm();
        long alarmPeriod = alarm.getDate().getTime();
        Calendar calendar = Calendar.getInstance();
        while (alarmPeriod < calendar.getTimeInMillis())
            alarmPeriod += AlarmManager.INTERVAL_DAY;
        Context context = alarmContext.getActivityContext();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, alarm.getIDsList().get(0), intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, alarmPeriod, pi);
    }

    public void cancelAlarm(AlarmContext alarmContext) {
        Context context = alarmContext.getActivityContext();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        Alarm alarm = alarmContext.getAlarm();
        PendingIntent sender = PendingIntent.getBroadcast(context, alarm.getIDsList().get(0),
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
