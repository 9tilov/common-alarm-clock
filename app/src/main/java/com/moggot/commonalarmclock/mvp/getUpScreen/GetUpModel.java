package com.moggot.commonalarmclock.mvp.getUpScreen;

import com.moggot.commonalarmclock.music.Music;

public interface GetUpModel {

    void loadAlarm(long id);

    void startVibro();

    void stopVibro();

    String getAlarmName();

    Music getMusic();

    boolean getIsSnoozeEnable();

    boolean getIsMathEnable();

    void cancelSingleAlarm();

    void snoozeAlarm();
}
