package com.moggot.commonalarmclock.mvp.getUpScreen;

import android.content.Context;
import android.os.Vibrator;

import com.moggot.commonalarmclock.AlarmScheduler;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.SnoozeAlarm;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.music.Music;

import java.util.Calendar;

public class GetUpModelImpl implements GetUpModel {

    private DataBase db;
    private Alarm alarm;
    private Vibrator vibrator;
    private Context context;

    public GetUpModelImpl(Context context) {
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
    public Music getMusic() {
        Music music = new Music(context);
        music.setMusic(alarm.getMusicTypeEnum(), alarm.getMusicPath());
        return music;
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
        SnoozeAlarm snoozeAlarm = new SnoozeAlarm(context);
        int requestCode = db.getRandomRequestCode();
        Calendar calendar = Calendar.getInstance();
        snoozeAlarm.setAlarm(alarm, requestCode, calendar.getTimeInMillis());
    }
}
