package com.moggot.commonalarmclock;

import android.content.Context;

import com.moggot.commonalarmclock.alarm.Alarm;

public class AlarmScheduler {

    private Context context;

    public AlarmScheduler(Context context) {
        this.context = context;
    }

    public void setAlarm(Alarm alarm) {
        Strategy strategy;
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) != 0)
            strategy = new SingleAlarmStrategy(context);
        else
            strategy = new RepeatAlarmStrategy(context);
        strategy.setAlarm(alarm);
    }

    public void cancelAlarm(Alarm alarm) {
        Strategy strategy;
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) != 0)
            strategy = new SingleAlarmStrategy(context);
        else
            strategy = new RepeatAlarmStrategy(context);
        strategy.cancelAlarm(alarm);
    }
}
