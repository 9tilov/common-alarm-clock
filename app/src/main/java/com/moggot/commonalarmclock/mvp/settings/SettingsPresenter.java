package com.moggot.commonalarmclock.mvp.settings;

import com.moggot.commonalarmclock.music.Music;

import java.util.Date;

public interface SettingsPresenter {

    void setModel(SettingsModel model);

    void setSettings(long id);

    void saveAlarm();

    Date getDate();

    void setDate(Date date);

    void setSnoozeCheckbox(boolean enable);

    void setMathCheckbox(boolean enable);

    void setName(String name);

    void setMusic(Music music);

    int getMusicCode();

    void setTomorrowDay();

    void setDayOn(int dayCode);

    void setDayOff(int dayCode);
}
