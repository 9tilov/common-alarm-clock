package com.moggot.commonalarmclock.mvp.settings;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.music.Music;

import java.util.Date;

public interface SettingsPresenter {

    void setModel(SettingsModel model);

    void setSettings(long id);

    void saveAlarm();

    int getHour();

    int getMinute();

    String getDateAsString();

    void setDate(Date date);

    void setIsSnoozeEnable(boolean enable);

    void setIsMathEnable(boolean enable);

    void setName(String name);

    void setMusic(Music music);

    int getMusicCode();

    void setTomorrowDay();

    void setDay(int dayCode);

    void deleteDay(int dayCode);

    SparseIntArray getDays();

}
