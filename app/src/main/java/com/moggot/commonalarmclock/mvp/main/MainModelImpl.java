package com.moggot.commonalarmclock.mvp.main;

import android.content.Context;

import com.moggot.commonalarmclock.schedule.AlarmScheduler;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainModelImpl implements MainModel {

    private static final String LOG_TAG = MainModelImpl.class.getSimpleName();

    private DataBase db;
    private Context context;
    private List<Alarm> alarms;

    public MainModelImpl(Context context) {
        this.context = context;
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

        deleteAlarmFromShedule(alarm);
    }

    private void deleteAlarmFromShedule(Alarm alarm) {
        AlarmScheduler alarmScheduler = new AlarmScheduler(context);
        alarmScheduler.cancelAlarm(alarm);
    }

    @Override
    public void editAlarm(Alarm alarm, int position) {
        alarms.set(position, alarm);
        db.editAlarm(alarm);

        editSheduleWithState(alarm);
    }

    private void editSheduleWithState(Alarm alarm) {
        AlarmScheduler alarmScheduler = new AlarmScheduler(context);
        if (alarm.getState())
            alarmScheduler.setAlarm(alarm);
        else
            alarmScheduler.cancelAlarm(alarm);
    }
}
