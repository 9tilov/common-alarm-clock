package com.moggot.commonalarmclock.presentation.mvp.presenter;

import com.moggot.commonalarmclock.MathExample;

public interface BuzzerActivityPresenter {

    void init(long id);

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
