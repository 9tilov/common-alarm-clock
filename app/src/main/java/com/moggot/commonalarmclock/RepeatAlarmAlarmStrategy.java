package com.moggot.commonalarmclock;

import android.app.*;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.SparseIntArray;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;

public class RepeatAlarmAlarmStrategy implements AlarmStrategy {

    private Context context;

    public RepeatAlarmAlarmStrategy(Context context) {
        this.context = context;
    }

    private static final String LOG_TAG = RepeatAlarmAlarmStrategy.class.getSimpleName();

    public void setAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        SparseIntArray ids = alarm.getRepeatAlarmIDs();

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (int i = 0; i < ids.size(); ++i) {
            PendingIntent pi = PendingIntent.getBroadcast(context, ids.valueAt(i), intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, ids.keyAt(i));
            long alarmPeriod = calendar.getTimeInMillis();
            Calendar calendarNow = Calendar.getInstance();
            while (alarmPeriod < calendarNow.getTimeInMillis())
                alarmPeriod += 7 * AlarmManager.INTERVAL_DAY;
            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmPeriod,
                    7 * android.app.AlarmManager.INTERVAL_DAY, pi);
        }
    }

    public void cancelAlarm(Alarm alarm) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        SparseIntArray ids = alarm.getRepeatAlarmIDs();
        for (int i = 0; i < ids.size(); ++i) {
            PendingIntent pi = PendingIntent.getBroadcast(context, ids.valueAt(i), intent, 0);
            am.cancel(pi);
        }
    }
}
