package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.settings.SettingsPresenter;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenterImpl;
import com.moggot.commonalarmclock.mvp.settings.SettingsView;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsScreenModule {

    private SettingsView settingsView;

    public SettingsScreenModule(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    @ScreenScope
    @Provides
    public SettingsView provideSettingsView() {
        return settingsView;
    }

    @ScreenScope
    @Provides
    public SettingsPresenter provideSettingsPresenter() {
        return new SettingsPresenterImpl(settingsView);
    }
}
