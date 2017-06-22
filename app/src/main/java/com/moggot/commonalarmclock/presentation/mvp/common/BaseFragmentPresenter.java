package com.moggot.commonalarmclock.mvp.common;

public interface BaseFragmentPresenter<View> {

    void setModel();

    void bindView(View view);
}
