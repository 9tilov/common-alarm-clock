package com.moggot.commonalarmclock.presentation.di.component;


import com.moggot.commonalarmclock.presentation.di.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.MainFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.SettingsFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = MainScreenModule.class)
public interface MainScreenComponent {

    void inject(MainFragment fragment);

    void inject(SettingsFragment fragment);
}
