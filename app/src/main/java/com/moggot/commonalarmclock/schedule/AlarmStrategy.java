package com.moggot.commonalarmclock.schedule;

import com.moggot.commonalarmclock.alarm.Alarm;

public interface AlarmStrategy {

    void setAlarm(Alarm alarm);

    void cancelAlarm(Alarm alarm);
}
