package com.moggot.commonalarmclock.mvp;

public interface BasePresenter {

    void initialize(long id);

    void onDestroy();
}
