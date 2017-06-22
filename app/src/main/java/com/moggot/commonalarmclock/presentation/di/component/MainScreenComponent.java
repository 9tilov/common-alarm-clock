package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.mvp.view.activity.MainActivity;
import com.moggot.commonalarmclock.mvp.view.fragments.MainFragment;
import com.moggot.commonalarmclock.mvp.view.fragments.SettingsFragment;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = MainScreenModule.class)
public interface MainScreenComponent {

    void inject(MainFragment fragment);

    void inject(SettingsFragment fragment);
}
