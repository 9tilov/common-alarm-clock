package com.moggot.commonalarmclock.presentation.mvp.model;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.music.Music;

import java.util.Date;

public interface SettingsModel {

    void loadAlarm(long id);

    void saveAlarm();

    String getDateAsString();

    Date getDate();

    void setDate(Date date);

    boolean getDaysCheckboxState();

    void setIsMathEnable(boolean enable);

    boolean getIsMathEnable();

    void setIsSnoozeEnable(boolean enable);

    boolean getIsSnoozeEnable();

    void setName(String name);

    String getName();

    void setMusic(Music music);

    int getMusicCode();

    void setTomorrowDay();

    void setDayOn(int dayCode);

    void setDayOff(int dayCode);

    SparseIntArray getRepeateIDs();

}
