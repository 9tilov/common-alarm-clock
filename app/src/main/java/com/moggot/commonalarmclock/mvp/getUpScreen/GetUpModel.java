package com.moggot.commonalarmclock.mvp.getUpScreen;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.music.Music;

/**
 * Created by toor on 29.05.17.
 */

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
