package com.moggot.commonalarmclock.mvp.presenter;

import android.text.Editable;

import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.mvp.common.BaseFragmentPresenter;
import com.moggot.commonalarmclock.mvp.view.SettingsFragmentView;

public interface SettingsFragmentPresenter extends BaseFragmentPresenter<SettingsFragmentView> {

    void loadAlarm(long id);

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