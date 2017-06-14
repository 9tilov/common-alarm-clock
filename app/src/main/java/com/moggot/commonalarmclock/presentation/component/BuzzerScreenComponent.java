package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.activity.ActivityBuzzer;
import com.moggot.commonalarmclock.presentation.modules.BuzzerScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = {BuzzerScreenModule.class})
public interface BuzzerScreenComponent {

    void inject(ActivityBuzzer activityBuzzer);
}
