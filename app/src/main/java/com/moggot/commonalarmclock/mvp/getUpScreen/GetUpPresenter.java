package com.moggot.commonalarmclock.mvp.getUpScreen;

public interface GetUpPresenter {

    void setModel(GetUpModel model);

    void startAlarmRing(long id);

    void stopAlarmRing();

    void snooze();

    void replaceFragment();
}
