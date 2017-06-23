package com.moggot.commonalarmclock.presentation.mvp.presenter;

public interface BuzzerActivityPresenter {

    void init(long id);

    void onClickSnooze();

    void checkResult(int result);
}
