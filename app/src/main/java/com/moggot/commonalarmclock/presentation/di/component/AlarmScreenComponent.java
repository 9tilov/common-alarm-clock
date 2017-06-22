package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.mvp.view.activity.BuzzerActivity;
import com.moggot.commonalarmclock.mvp.view.fragments.CommonFragment;
import com.moggot.commonalarmclock.mvp.view.fragments.MathFragment;
import com.moggot.commonalarmclock.mvp.view.fragments.SnoozeFragment;
import com.moggot.commonalarmclock.presentation.modules.AlarmScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = AlarmScreenModule.class)
public interface AlarmScreenComponent {

    void inject(BuzzerActivity activity);

    void inject(CommonFragment fragment);

    void inject(MathFragment fragment);

    void inject(SnoozeFragment fragment);
}
