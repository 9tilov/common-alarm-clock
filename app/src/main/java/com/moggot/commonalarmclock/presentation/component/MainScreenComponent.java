package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.fragments.MainFragment;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = {MainScreenModule.class})
public interface MainScreenComponent {

    void inject(MainFragment mainFragment);
}
