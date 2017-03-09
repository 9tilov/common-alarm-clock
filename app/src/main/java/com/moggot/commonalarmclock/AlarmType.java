package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 03.03.17.
 */

public interface AlarmType {

    void setAlarm(AlarmContext alarmContext);

    void cancelAlarm(AlarmContext alarmContext);

}
