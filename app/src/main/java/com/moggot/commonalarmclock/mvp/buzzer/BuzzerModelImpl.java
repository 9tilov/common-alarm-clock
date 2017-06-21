package com.moggot.commonalarmclock.mvp.buzzer;

import android.content.Context;
import android.os.Vibrator;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;
import com.moggot.commonalarmclock.schedule.SnoozeAlarm;

import java.util.Calendar;

public class BuzzerModelImpl implements BuzzerModel {

    private DataBase db;
    private Alarm alarm;
    private Vibrator vibrator;
    private Context context;

    public BuzzerModelImpl(Context context) {
        this.context = context;
        this.db = new DataBase(context);
    }

    public void loadAlarm(long id) {
        alarm = db.getAlarm(id);
    }

    @Override
    public void startVibro() {
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] once = {0, 500, 500};
        vibrator.vibrate(once, 0);
    }

    @Override
    public void stopVibro() {
        vibrator.cancel();
    }

    @Override
    public String getAlarmName() {
        return alarm.getName();
    }

    @Override
    public int getMusicType() {
        return alarm.getMusicType();
    }

    @Override
    public String getMusicPath() {
        return alarm.getMusicPath();
    }

    @Override
    public boolean getIsSnoozeEnable() {
        return alarm.getIsSnoozeEnable();
    }

    @Override
    public boolean getIsMathEnable() {
        return alarm.getIsMathEnable();
    }

    @Override
    public void cancelSingleAlarm() {
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) != 0) {
            AlarmScheduler alarmScheduler = new AlarmScheduler(context);
            alarmScheduler.cancelAlarm(alarm);
            alarm.setState(false);
        }
    }

    @Override
    public void snoozeAlarm() {
        int requestCode = db.getRandomRequestCode();
        Calendar calendar = Calendar.getInstance();
        SnoozeAlarm snoozeAlarm = new SnoozeAlarm(requestCode, calendar.getTimeInMillis());
        snoozeAlarm.setAlarm(alarm);
    }
}
