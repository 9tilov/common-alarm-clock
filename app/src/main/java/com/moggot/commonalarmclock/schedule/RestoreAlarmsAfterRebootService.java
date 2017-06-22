package com.moggot.commonalarmclock.schedule;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.moggot.commonalarmclock.data.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.List;

/**
 * Данный сервис запускается сразу после перезагрузки
 * для того, чтобы восстановить расписание для всех активных будильников
 */
public class RestoreAlarmsAfterRebootService extends IntentService {

    public RestoreAlarmsAfterRebootService() {
        super(RestoreAlarmsAfterRebootService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        restartAllActiveAlarms();
    }

    private void restartAllActiveAlarms() {
        DataBase db = new DataBase(this);
        List<Alarm> activeAlarms = db.getAllActiveAlarms();
        AlarmScheduler alarmScheduler = new AlarmScheduler(this);
        for (Alarm alarm : activeAlarms) {
            alarmScheduler.setAlarm(alarm);
        }
    }
}
