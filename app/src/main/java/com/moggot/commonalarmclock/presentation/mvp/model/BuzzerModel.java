package com.moggot.commonalarmclock.mvp.model;

public interface BuzzerModel {

    void loadAlarm(long id);

    void startVibro();

    void stopVibro();

    String getAlarmName();

    int getMusicType();

    String getMusicPath();

    boolean getIsSnoozeEnable();

    boolean getIsMathEnable();

    void cancelSingleAlarm();

    void snoozeAlarm();
}
