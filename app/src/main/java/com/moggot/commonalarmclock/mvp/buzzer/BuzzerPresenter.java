package com.moggot.commonalarmclock.mvp.buzzer;

import com.moggot.commonalarmclock.MathExample;

public interface BuzzerPresenter {

    void initialize(long id);

    void onDestroy();

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
