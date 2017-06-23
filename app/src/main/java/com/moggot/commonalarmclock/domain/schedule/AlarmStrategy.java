package com.moggot.commonalarmclock.domain.schedule;

import com.moggot.commonalarmclock.data.alarm.Alarm;

public interface AlarmStrategy {

    void setAlarm(Alarm alarm);

    void cancelAlarm(Alarm alarm);
}
