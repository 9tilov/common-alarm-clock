package com.moggot.commonalarmclock.mvp.getUpScreen;

import com.moggot.commonalarmclock.MathExample;

public interface GetUpPresenter {

    void initialize(long id);

    void onDestroy();

    void onClickSnooze();

    void checkMathExample(MathExample example);
}
