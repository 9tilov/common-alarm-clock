package com.moggot.commonalarmclock.mvp.presenter;

import android.text.Editable;

import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.mvp.model.SettingsModel;
import com.moggot.commonalarmclock.mvp.model.SettingsModelImpl;
import com.moggot.commonalarmclock.mvp.view.SettingsFragmentView;
import com.moggot.commonalarmclock.presentation.App;
import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class SettingsFragmentPresenterImpl implements SettingsFragmentPresenter {

    private SettingsFragmentView view;
    private SettingsModel model;

    @Inject
    DataBase dataBase;

    @Inject
    AlarmScheduler alarmScheduler;

    @Inject
    public SettingsFragmentPresenterImpl() {
        App.getInstance().getAppComponent().plus(new AlarmModule()).inject(this);
        setModel();
    }

    @Override
    public void setModel() {
        this.model = new SettingsModelImpl(dataBase, alarmScheduler);
    }

    @Override
    public void bindView(SettingsFragmentView view) {
        this.view = view;
    }

    @Override
    public void loadAlarm(long id) {
        model.loadAlarm(id);
    }

    @Override
    public void setupViews() {
        view.setTime(model.getDateAsString());
        view.setDaysCheckboxChecked(model.getDaysCheckboxState());
        view.setDaysButtons(model.getRepeateIDs());
        view.setIsSnoozeEnable(model.getIsSnoozeEnable());
        view.setIsMathEnable(model.getIsMathEnable());
        view.setName(model.getName());
        view.setRadioButtonAndMusicButton(model.getMusicCode());
    }

    @Override
    public void saveAlarm() {
        model.saveAlarm();
    }

    @Override
    public void afterTextChanged(Editable s) {
        model.setName(s.toString());
    }

    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.MINUTE);
    }

    public void setSelectedTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date date = new Date(calendar.getTimeInMillis());
        model.setDate(date);
        view.setTime(model.getDateAsString());
    }

    @Override
    public void setDayOn(int dayCode) {
        model.setDayOn(dayCode);
    }

    @Override
    public void setDayOff(int dayCode) {
        model.setDayOff(dayCode);
    }

    @Override
    public void setCheckedSnooze(boolean isChecked) {
        model.setIsSnoozeEnable(isChecked);
    }

    @Override
    public void setCheckedRepeate(boolean isChecked) {
        if (isChecked)
            view.showDays();
        else {
            view.hideDays();
            model.setTomorrowDay();
        }
    }

    @Override
    public void setCheckedMath(boolean isChecked) {
        model.setIsMathEnable(isChecked);
    }

    @Override
    public void setMusic(Music music) {
        model.setMusic(music);
    }
}