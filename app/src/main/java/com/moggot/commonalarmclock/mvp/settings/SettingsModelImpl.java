package com.moggot.commonalarmclock.mvp.settings;

import android.content.Context;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.domain.utils.NetworkConnectionChecker;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;

import java.util.Calendar;
import java.util.Date;

public class SettingsModelImpl implements SettingsModel {

    private DataBase db;
    private Context context;
    private Alarm alarm;

    public SettingsModelImpl(Context context) {
        this.db = new DataBase(context);
        this.context = context;
    }

    @Override
    public void loadAlarm(long id) {
        if (id == Consts.NO_ID)
            createAlarm();
        else
            alarm = db.getAlarm(id);
    }

    private void createAlarm() {
        alarm = new Alarm();
        alarm.setDate(getCurrentDate());
        alarm.setRequestCodes(generateRequestCodesAsString());
        alarm.setIsSnoozeEnable(false);
        alarm.setIsMathEnable(true);
        alarm.setName("");
        alarm.setMusic(new Music(context));
        alarm.setState(true);
    }

    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new Date(calendar.getTimeInMillis());
    }

    private String generateRequestCodesAsString() {
        SparseIntArray ids = new SparseIntArray();
        int requstCode = db.getRandomRequestCode();
        ids.put(Consts.TOMORROW, requstCode);
        return new Gson().toJson(ids);
    }

    @Override
    public void saveAlarm() {
        alarm.setState(true);
        Long id = alarm.getId();
        if (id == null)
            db.addAlarm(alarm);
        else {
            Alarm loadedAlarm = db.getAlarm(id);
            cancelOldAlarm(loadedAlarm);
            db.editAlarm(alarm);
        }
        addAlarmToShedule(alarm);
    }

    private void cancelOldAlarm(Alarm oldAlarm) {
        AlarmScheduler alarmScheduler = new AlarmScheduler(context);
        alarmScheduler.cancelAlarm(oldAlarm);
    }

    private void addAlarmToShedule(Alarm alarm) {
        AlarmScheduler alarmScheduler = new AlarmScheduler(context);
        alarmScheduler.setAlarm(alarm);
    }

    @Override
    public String getDateAsString() {
        return convertDateToString(alarm.getDate());
    }

    @Override
    public Date getDate() {
        return alarm.getDate();
    }

    @Override
    public void setDate(Date date) {
        alarm.setDate(date);
    }

    private String convertDateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getTimeStr(calendar.getTimeInMillis());
    }

    private String getTimeStr(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return pad(hour) + ":" + pad(minute);
    }

    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public boolean getIsMathEnable() {
        return alarm.getIsMathEnable();
    }

    @Override
    public void setIsMathEnable(boolean enable) {
        alarm.setIsMathEnable(enable);
    }

    @Override
    public boolean getDaysCheckboxState() {
        return !(alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) > 0);
    }

    @Override
    public boolean getIsSnoozeEnable() {
        return alarm.getIsSnoozeEnable();
    }

    @Override
    public void setName(String name) {
        alarm.setName(name);
    }

    @Override
    public void setIsSnoozeEnable(boolean enable) {
        alarm.setIsSnoozeEnable(enable);
    }

    @Override
    public String getName() {
        return alarm.getName();
    }

    @Override
    public void setMusic(Music music) {
        alarm.setMusic(music);
    }

    @Override
    public int getMusicCode() {
        return alarm.getMusicType();
    }

    @Override
    public void setTomorrowDay() {
        SparseIntArray ids = new SparseIntArray();
        ids.put(Consts.TOMORROW, db.getRandomRequestCode());
        alarm.setRepeatAlarmIDs(ids);
    }

    @Override
    public void setDayOn(int dayCode) {
        SparseIntArray ids = alarm.getRepeatAlarmIDs();
        if (ids.get(Consts.TOMORROW) != 0)
            ids.clear();
        int requestCode = db.getRandomRequestCode();
        ids.put(dayCode, requestCode);
        alarm.setRepeatAlarmIDs(ids);

        if (ids.size() == 0)
            setTomorrowDay();
    }

    @Override
    public void setDayOff(int dayCode) {
        SparseIntArray ids = alarm.getRepeatAlarmIDs();
        ids.delete(dayCode);
        alarm.setRepeatAlarmIDs(ids);

        if (ids.size() == 0)
            setTomorrowDay();
    }

    @Override
    public SparseIntArray getRepeateIDs() {
        return alarm.getRepeatAlarmIDs();
    }

    @Override
    public boolean isNetworkAvailable() {
        NetworkConnectionChecker connectionChecker = new NetworkConnectionChecker(context);
        return connectionChecker.isNetworkAvailable();
    }
}