package com.moggot.commonalarmclock.presentation.di.component;

import com.moggot.commonalarmclock.presentation.di.modules.AlarmScreenModule;
import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.BuzzerActivity;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.CommonFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.MathFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.SnoozeFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = AlarmScreenModule.class)
public interface AlarmScreenComponent {

    void inject(BuzzerActivity activity);

    void inject(CommonFragment fragment);

    void inject(MathFragment fragment);

    void inject(SnoozeFragment fragment);
}
