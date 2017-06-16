package com.moggot.commonalarmclock.mvp.settings;

import android.util.SparseIntArray;

import com.moggot.commonalarmclock.mvp.BaseView;

public interface SettingsView extends BaseView {

    void setTime(String time);

    void setDaysCheckbox(boolean isChecked);

    void setDays(SparseIntArray ids);

    void showDays();

    void hideDays();

    void setIsSnoozeEnable(boolean isSnoozeEnable);

    void setIsMathEnable(boolean isMathEnable);

    void setName(String name);

    void setMusicButton(int musicType);

    void setButtonRadioDrawable(boolean isPlaying);

    void showToastNoMusicFile();
}
