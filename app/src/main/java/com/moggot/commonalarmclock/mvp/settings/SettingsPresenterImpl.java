package com.moggot.commonalarmclock.mvp.settings;

import android.text.Editable;

import com.moggot.commonalarmclock.music.Music;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsView settingsView;
    private SettingsModel settingsModel;

    @Inject
    public SettingsPresenterImpl(SettingsView settingsView, SettingsModelImpl settingsModel) {
        this.settingsView = settingsView;
        this.settingsModel = settingsModel;
    }

    @Override
    public void init(long id) {
        settingsModel.loadAlarm(id);
    }

    @Override
    public void setupViews() {
        settingsView.setTime(settingsModel.getDateAsString());
        settingsView.setDaysCheckboxChecked(settingsModel.getDaysCheckboxState());
        settingsView.setDaysButtons(settingsModel.getRepeateIDs());
        settingsView.setIsSnoozeEnable(settingsModel.getIsSnoozeEnable());
        settingsView.setIsMathEnable(settingsModel.getIsMathEnable());
        settingsView.setName(settingsModel.getName());
        settingsView.setRadioButtonAndMusicButton(settingsModel.getMusicCode());
    }

    @Override
    public void saveAlarm() {
        settingsModel.saveAlarm();
    }

    @Override
    public void afterTextChanged(Editable s) {
        settingsModel.setName(s.toString());
    }

    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(settingsModel.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(settingsModel.getDate());
        return calendar.get(Calendar.MINUTE);
    }

    public void setSelectedTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date date = new Date(calendar.getTimeInMillis());
        settingsModel.setDate(date);
        settingsView.setTime(settingsModel.getDateAsString());
    }

    @Override
    public void setDayOn(int dayCode) {
        settingsModel.setDayOn(dayCode);
    }

    @Override
    public void setDayOff(int dayCode) {
        settingsModel.setDayOff(dayCode);
    }

    @Override
    public void setCheckedSnooze(boolean isChecked) {
        settingsModel.setIsSnoozeEnable(isChecked);
    }

    @Override
    public void setCheckedRepeate(boolean isChecked) {
        if (isChecked)
            settingsView.showDays();
        else {
            settingsView.hideDays();
            settingsModel.setTomorrowDay();
        }
    }

    @Override
    public void setCheckedMath(boolean isChecked) {
        settingsModel.setIsMathEnable(isChecked);
    }

    @Override
    public void setMusic(Music music) {
        settingsModel.setMusic(music);
    }

}