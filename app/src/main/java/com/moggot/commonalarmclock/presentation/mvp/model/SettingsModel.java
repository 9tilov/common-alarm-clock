package com.moggot.commonalarmclock.presentation.mvp.model;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.domain.music.Music;

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

    void setMusicType(int type);

    void setMusicPath(String path);

    int getMusicType();

    String getMusicPath();

    void setTomorrowDay();

    void setDayOn(int dayCode);

    void setDayOff(int dayCode);

    SparseIntArray getRepeateIDs();

}
