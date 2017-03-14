package com.moggot.commonalarmclock;

import android.app.*;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;

/**
 * Created by toor on 13.03.17.
 */

public class SnoozeAlarmDecorator extends Decorator {

    private int snoozeCount = 0;
    private int requestCode = 0;

    public SnoozeAlarmDecorator(AlarmType alarmType) {
        super(alarmType);
    }

    @Override
    public void setAlarm(AlarmContext alarmContext) {
        Context context = alarmContext.getActivityContext();
        Alarm alarm = alarmContext.getAlarm();
        long id = alarm.getId();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, id);
        DataBase db = new DataBase(context);
        requestCode = db.getRandomRequestCode();
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        android.app.AlarmManager am = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long alarmPeriod = alarm.getTimeInMillis();
        Calendar calendar = Calendar.getInstance();
        while (alarmPeriod < calendar.getTimeInMillis())
            alarmPeriod += AlarmManager.INTERVAL_DAY;
        ++snoozeCount;
        alarmPeriod += snoozeCount * 60000;
        am.set(android.app.AlarmManager.RTC_WAKEUP, alarmPeriod, pi);
        super.setAlarm(alarmContext);
    }

    @Override
    public void cancelAlarm(AlarmContext alarmContext) {
        Context context = alarmContext.getActivityContext();
        Alarm alarm = alarmContext.getAlarm();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        super.cancelAlarm(alarmContext);
    }
}
