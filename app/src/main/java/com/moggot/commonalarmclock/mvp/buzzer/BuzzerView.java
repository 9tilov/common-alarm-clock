package com.moggot.commonalarmclock.mvp.buzzer;

import com.moggot.commonalarmclock.mvp.BaseView;

public interface BuzzerView extends BaseView {

    void setupView();

    void showToastIncorrectResult();
}