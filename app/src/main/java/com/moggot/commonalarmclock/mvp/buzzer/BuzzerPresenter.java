package com.moggot.commonalarmclock.mvp.buzzer;

import com.moggot.commonalarmclock.MathExample;

public interface BuzzerPresenter {

    void init(long id);

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
