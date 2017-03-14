package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 13.03.17.
 */

public class Decorator implements AlarmType {

    protected AlarmType alarmType;

    public Decorator(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public void setAlarm(AlarmContext alarmContext) {
        alarmType.setAlarm(alarmContext);
    }

    public void cancelAlarm(AlarmContext alarmContext) {
        alarmType.cancelAlarm(alarmContext);
    }
}
