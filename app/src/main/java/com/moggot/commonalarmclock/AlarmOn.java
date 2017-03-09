package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 03.03.17.
 */

public class AlarmOn implements AlarmState {

    private DataBase db;
    private AlarmContext alarmContext;

    public AlarmOn(AlarmContext alarmContext) {
        this.alarmContext = alarmContext;
        db = new DataBase(alarmContext.getActivityContext());
    }

    public void setType(AlarmType alarmType) {
        Alarm alarm = alarmContext.getAlarm();
        alarmType.setAlarm(alarmContext);
        alarm.setState(true);
        db.editAlarm(alarm);
        alarmContext.setState(this);
    }
}
