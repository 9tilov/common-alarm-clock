package com.moggot.commonalarmclock.alarm.mvp.settings;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.alarm.mvp.BaseView;

public interface SettingsView extends BaseView {

    void setTime(String time);

    void setDaysCheckbox(boolean isChecked);

    void setDays(SparseIntArray ids);

    void setIsSnoozeEnable(boolean isSnoozeEnable);

    void setIsMathEnable(boolean isMathEnable);

    void setName(String name);

    void setMusic(int musicType);
}
