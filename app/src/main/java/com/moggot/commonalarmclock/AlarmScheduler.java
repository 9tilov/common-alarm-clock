package com.moggot.commonalarmclock;

import android.content.Context;

import com.moggot.commonalarmclock.alarm.Alarm;

public class AlarmScheduler {

    private Context context;

    public AlarmScheduler(Context context) {
        this.context = context;
    }

    public void setAlarm(Alarm alarm) {
        AlarmStrategy alarmStrategy;
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) != 0)
            alarmStrategy = new SingleAlarmAlarmStrategy(context);
        else
            alarmStrategy = new RepeatAlarmAlarmStrategy(context);
        alarmStrategy.setAlarm(alarm);
    }

    public void cancelAlarm(Alarm alarm) {
        AlarmStrategy alarmStrategy;
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) != 0)
            alarmStrategy = new SingleAlarmAlarmStrategy(context);
        else
            alarmStrategy = new RepeatAlarmAlarmStrategy(context);
        alarmStrategy.cancelAlarm(alarm);
    }
}
