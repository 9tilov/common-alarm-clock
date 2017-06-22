package com.moggot.commonalarmclock.presentation.mvp.common;

public interface BaseFragmentPresenter<View> {

    void setModel();

    void bindView(View view);
}
