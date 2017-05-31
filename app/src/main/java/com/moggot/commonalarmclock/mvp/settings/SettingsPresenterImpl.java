package com.moggot.commonalarmclock.mvp.settings;

import com.moggot.commonalarmclock.music.Music;

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
        view.setMusicButton(model.getMusicCode());
    }

    @Override
    public void saveAlarm() {
        model.saveAlarm();
    }

    @Override
    public Date getDate() {
        return model.getDate();
    }

    @Override
    public void setDate(Date date) {
        model.setDate(date);
        view.setTime(model.getDateAsString());
    }

    @Override
    public void setSnoozeCheckbox(boolean enable) {
        model.setIsSnoozeEnable(enable);
    }

    @Override
    public void setMathCheckbox(boolean enable) {
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
    public void setDayOn(int dayCode) {
        model.setDayOn(dayCode);
    }

    @Override
    public void setDayOff(int dayCode) {
        model.setDayOff(dayCode);
    }
}