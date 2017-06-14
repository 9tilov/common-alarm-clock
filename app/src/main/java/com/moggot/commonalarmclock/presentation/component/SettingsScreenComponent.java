package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.fragments.SettingsFragment;
import com.moggot.commonalarmclock.presentation.modules.SettingsScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = {SettingsScreenModule.class})
public interface SettingsScreenComponent {

    void inject(SettingsFragment settingsFragment);
}
