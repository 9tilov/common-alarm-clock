package com.moggot.commonalarmclock.mvp.buzzer;

import com.moggot.commonalarmclock.MathExample;
import com.moggot.commonalarmclock.mvp.BasePresenter;

public interface BuzzerPresenter extends BasePresenter{

    void onDestroy();

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
