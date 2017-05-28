package com.moggot.commonalarmclock.mvp.main;

import android.content.Context;

import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainModelImpl implements MainModel {

    private static final String LOG_TAG = MainModelImpl.class.getSimpleName();

    private DataBase db;
    private List<Alarm> alarms;

    public MainModelImpl(Context context) {
        this.db = new DataBase(context);
        this.alarms = new ArrayList<>();
    }

    @Override
    public Alarm getAlarm(int position) {
        return alarms.get(position);
    }

    @Override
    public int getAlarmsCount() {
        return alarms.size();
    }

    @Override
    public void loadData() {
        alarms = db.getAllAlarms();
    }

    @Override
    public void deleteAlarm(int position) {
        Alarm alarm = alarms.get(position);
        db.deleteAlarm(alarm);
        alarms.remove(position);
    }

    @Override
    public void editAlarm(Alarm alarm, int position) {
        alarms.set(position, alarm);
        db.editAlarm(alarm);
    }
}
