package com.moggot.commonalarmclock;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 09.03.17.
 */

public class AlarmManager implements AlarmType {

    public void setAlarm(AlarmContext alarmContext) {
        AlarmOn on = new AlarmOn(alarmContext);
        Alarm alarm = alarmContext.getAlarm();
        SparseIntArray ids = alarm.getIDs();
        if (ids.get(Consts.TOMORROW) != 0)
            on.setType(new SingleAlarm());
        else
            on.setType(new RepeatAlarm());
    }

    public void cancelAlarm(AlarmContext alarmContext) {
        AlarmOff off = new AlarmOff(alarmContext);
        Alarm alarm = alarmContext.getAlarm();
        SparseIntArray ids = alarm.getIDs();
        if (ids.get(Consts.TOMORROW) != 0)
            off.setType(new SingleAlarm());
        else
            off.setType(new RepeatAlarm());
    }
}
