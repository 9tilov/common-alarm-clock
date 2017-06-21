package com.moggot.commonalarmclock.mvp.settings;

import android.text.Editable;

import com.moggot.commonalarmclock.music.Music;

public interface SettingsPresenter {

    void init(long id);

    void setupViews();

    void saveAlarm();

    int getHour();

    int getMinute();

    void setSelectedTime(int hour, int minute);

    void setDayOn(int dayCode);

    void setDayOff(int dayCode);

    void setCheckedSnooze(boolean isChecked);

    void setCheckedRepeate(boolean isChecked);

    void setCheckedMath(boolean isChecked);

    void setMusic(Music music);

    void afterTextChanged(Editable s);
}