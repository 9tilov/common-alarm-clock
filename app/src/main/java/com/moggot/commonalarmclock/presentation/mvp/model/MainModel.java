package com.moggot.commonalarmclock.mvp.model;

import com.moggot.commonalarmclock.alarm.Alarm;

public interface MainModel {

    void loadData();

    void deleteAlarm(int position);

    void editAlarm(Alarm alarm, int position);

    Alarm getAlarm(int position);

    int getAlarmsCount();
}