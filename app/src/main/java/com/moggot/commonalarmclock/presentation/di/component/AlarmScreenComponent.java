package com.moggot.commonalarmclock.presentation.di.component;

import com.moggot.commonalarmclock.presentation.di.modules.AlarmScreenModule;
import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.BuzzerActivity;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.BuzzerMathFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.BuzzerCommonFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.BuzzerSnoozeFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = AlarmScreenModule.class)
public interface AlarmScreenComponent {

    void inject(BuzzerActivity activity);

    void inject(BuzzerCommonFragment fragment);

    void inject(BuzzerMathFragment fragment);

    void inject(BuzzerSnoozeFragment fragment);
}
