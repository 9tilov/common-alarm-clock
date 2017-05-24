package com.moggot.commonalarmclock.main;

import android.content.Context;

import com.moggot.commonalarmclock.AlarmContext;
import com.moggot.commonalarmclock.AlarmManager;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.List;

public class MainModelImpl implements MainModel {

    private DataBase db;
    private Context context;
    private List<Alarm> alarms;

    public MainModelImpl(Context context) {
        this.db = new DataBase(context);
        this.context = context;
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
        AlarmContext alarmContext = new AlarmContext(alarm, context);
        AlarmManager alarmManager = new AlarmManager();
        if (alarm.getState())
            alarmManager.setAlarm(alarmContext);
        else
            alarmManager.cancelAlarm(alarmContext);
    }

    @Override
    public void addAlarm(Alarm alarm) {
        db.addAlarm(alarm);
        alarms.add(alarm);
    }
}
