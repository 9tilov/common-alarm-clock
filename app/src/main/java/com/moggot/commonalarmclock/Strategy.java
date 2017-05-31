package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

public interface Strategy {

    void setAlarm(Alarm alarm);

    void cancelAlarm(Alarm alarm);
}
