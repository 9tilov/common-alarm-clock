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
        if (ids.get(Consts.DAYS.TOMORROW.getCode()) != 0) {
            if (alarm.getIsSnoozeEnable())
                on.setType(new SnoozeAlarmDecorator(new SingleAlarm()));
            else
                on.setType(new SingleAlarm());
        } else {
            if (alarm.getIsSnoozeEnable())
                on.setType(new SnoozeAlarmDecorator(new RepeateAlarm()));
            else
                on.setType(new RepeateAlarm());
        }
    }

    public void cancelAlarm(AlarmContext alarmContext) {
        AlarmOff off = new AlarmOff(alarmContext);
        Alarm alarm = alarmContext.getAlarm();
        SparseIntArray ids = alarm.getIDs();
        if (ids.get(Consts.DAYS.TOMORROW.getCode()) != 0) {
            if (alarm.getIsSnoozeEnable())
                off.setType(new SnoozeAlarmDecorator(new SingleAlarm()));
            else
                off.setType(new SingleAlarm());
        } else {
            if (alarm.getIsSnoozeEnable())
                off.setType(new SnoozeAlarmDecorator(new RepeateAlarm()));
            else
                off.setType(new RepeateAlarm());
        }
    }
}
