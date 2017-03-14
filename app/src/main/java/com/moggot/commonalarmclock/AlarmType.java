package com.moggot.commonalarmclock;

/**
 * Created by toor on 03.03.17.
 */

public interface AlarmType {

    void setAlarm(AlarmContext alarmContext);

    void cancelAlarm(AlarmContext alarmContext);

}
