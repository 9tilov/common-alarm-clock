package com.moggot.commonalarmclock.alarm.mvp.settings;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.music.Music;

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
        alarm.setMusicPath(createMusicPath());
        alarm.setMusicType(createMusicType());
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

    private String createMusicPath() {
        if (isNetworkAvailable())
            return Consts.DATA_RADIO;
        else
            return Consts.DATA_DEFAULT_RINGTONE;
    }

    private int createMusicType() {
        if (isNetworkAvailable())
            return Consts.MUSIC_TYPE.RADIO.getCode();
        else
            return Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getCode();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    @Override
    public void saveAlarm() {
        if (alarm.getId() == null)
            db.addAlarm(alarm);
        else
            db.editAlarm(alarm);
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
    public void setMusic(int musicType, String musicPath) {
        alarm.setMusic(musicType, musicPath);
    }

    @Override
    public int getMusicType() {
        return alarm.getMusicType();
    }

    @Override
    public SparseIntArray getRepeateIDs() {
        return alarm.getRepeatAlarmIDs();
    }
}