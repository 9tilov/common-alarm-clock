package com.moggot.commonalarmclock;

import android.app.*;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;
import java.util.List;

/**
 * Created by toor on 01.03.17.
 */

public class RepeateAlarm implements AlarmType {

    public void setAlarm(AlarmContext alarmContext) {
        Context context = alarmContext.getActivityContext();
        Alarm alarm = alarmContext.getAlarm();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        List<Integer> ids = alarm.getIDsList();
        for (Integer id : ids) {
            PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
            long alarmPeriod = alarm.getDate().getTime();
            Calendar calendar = Calendar.getInstance();
            while (alarmPeriod < calendar.getTimeInMillis())
                alarmPeriod += 7 * AlarmManager.INTERVAL_DAY;
            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmPeriod,
                    7 * android.app.AlarmManager.INTERVAL_DAY, pi);
        }

    }

    public void cancelAlarm(AlarmContext alarmContext) {
        Context context = alarmContext.getActivityContext();
        Alarm alarm = alarmContext.getAlarm();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        List<Integer> ids = alarm.getIDsList();
        for (Integer id : ids) {
            PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
            am.cancel(pi);
        }
    }
}
