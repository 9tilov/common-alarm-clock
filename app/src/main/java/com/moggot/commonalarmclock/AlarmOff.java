package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 04.03.17.
 */

public class AlarmOff implements AlarmState {

    private DataBase db;
    private AlarmContext alarmContext;

    public AlarmOff(AlarmContext alarmContext) {
        this.alarmContext = alarmContext;
        db = new DataBase(alarmContext.getActivityContext());
    }

    public void setType(AlarmType alarmType) {
        Alarm alarm = alarmContext.getAlarm();
        alarmType.cancelAlarm(alarmContext);
        alarm.setState(false);
        db.editAlarm(alarm);
        alarmContext.setState(this);
    }
}
