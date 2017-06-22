package com.moggot.commonalarmclock.mvp.presenter;

import com.moggot.commonalarmclock.MathExample;

public interface BuzzerActivityPresenter {

    void init(long id);

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
