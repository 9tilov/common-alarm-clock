package com.moggot.commonalarmclock.mvp.settings;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.music.Music;

import java.util.Calendar;
import java.util.Date;

public class SettingsPresenterImpl implements SettingsPresenter {

    private static final String LOG_TAG = SettingsPresenterImpl.class.getSimpleName();

    private SettingsView view;
    private SettingsModel model;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
    }

    public void setModel(SettingsModel model) {
        this.model = model;
    }

    @Override
    public void setSettings(long id) {
        model.loadAlarm(id);
        view.setTime(model.getDateAsString());
        view.setDaysCheckbox(model.getDaysCheckboxState());
        view.setDays(model.getRepeateIDs());
        view.setIsSnoozeEnable(model.getIsSnoozeEnable());
        view.setIsMathEnable(model.getIsMathEnable());
        view.setName(model.getName());
        view.setMusicRadioButton(model.getMusicCode());
    }

    @Override
    public void saveAlarm() {
        model.saveAlarm();
    }

    @Override
    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.MINUTE);
    }

    @Override
    public String getDateAsString() {
        return model.getDateAsString();
    }

    @Override
    public void setDate(Date date) {
        model.setDate(date);
    }

    @Override
    public void setIsSnoozeEnable(boolean enable) {
        model.setIsSnoozeEnable(enable);
    }

    @Override
    public void setIsMathEnable(boolean enable) {
        model.setIsMathEnable(enable);
    }

    @Override
    public void setName(String name) {
        model.setName(name);
    }

    @Override
    public void setMusic(Music music) {
        model.setMusic(music);
    }

    @Override
    public int getMusicCode() {
        return model.getMusicCode();
    }

    @Override
    public void setTomorrowDay() {
        model.setTomorrowDay();
    }

    @Override
    public void setDay(int dayCode) {
        model.setDay(dayCode);
    }

    @Override
    public void deleteDay(int dayCode) {
        model.deleteDay(dayCode);
    }

    @Override
    public SparseIntArray getDays() {
        return model.getDays();
    }
}