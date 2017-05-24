package com.moggot.commonalarmclock.main;

import com.moggot.commonalarmclock.alarm.Alarm;

public interface MainModel {

    void loadData();

    void deleteAlarm(int position);

    void editAlarm(Alarm alarm, int position);

    void addAlarm(Alarm alarm);

    Alarm getAlarm(int position);

    int getAlarmsCount();
}
