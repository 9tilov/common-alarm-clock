package com.moggot.commonalarmclock.presentation.mvp.view;

import android.util.SparseIntArray;

public interface SettingsFragmentView {

    void setTime(String time);

    void setDaysCheckboxChecked(boolean isChecked);

    void setDaysButtons(SparseIntArray ids);

    void showDays();

    void hideDays();

    void setIsSnoozeEnable(boolean isSnoozeEnable);

    void setIsMathEnable(boolean isMathEnable);

    void setName(String name);

    void setRadioButtonAndMusicButton(int radioGroupID);

    void setOnMusicRadioButton();

    void setOffMusicRadioButton();

}
