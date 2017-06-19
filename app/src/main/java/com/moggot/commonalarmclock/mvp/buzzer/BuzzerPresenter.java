package com.moggot.commonalarmclock.mvp.buzzer;

import com.moggot.commonalarmclock.MathExample;
import com.moggot.commonalarmclock.mvp.BasePresenter;

public interface BuzzerPresenter extends BasePresenter {

    void init(long id);

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
