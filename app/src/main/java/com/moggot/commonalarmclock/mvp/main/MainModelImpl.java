package com.moggot.commonalarmclock.mvp.main;

import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainModelImpl implements MainModel {

    private DataBase db;
    private List<Alarm> alarms;
    private AlarmScheduler alarmScheduler;

    @Inject
    public MainModelImpl(DataBase dataBase, AlarmScheduler alarmScheduler) {
        this.db = dataBase;
        this.alarmScheduler = alarmScheduler;
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
        alarmScheduler.cancelAlarm(alarm);
    }

    @Override
    public void editAlarm(Alarm alarm, int position) {
        alarms.set(position, alarm);
        db.editAlarm(alarm);

        editSheduleWithState(alarm);
    }

    private void editSheduleWithState(Alarm alarm) {
        if (alarm.getState())
            alarmScheduler.setAlarm(alarm);
        else
            alarmScheduler.cancelAlarm(alarm);
    }
}
