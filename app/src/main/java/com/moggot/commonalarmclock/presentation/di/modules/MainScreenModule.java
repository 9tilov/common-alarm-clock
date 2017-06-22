package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.presenter.MainFragmentPresenter;
import com.moggot.commonalarmclock.mvp.presenter.MainFragmentPresenterImpl;
import com.moggot.commonalarmclock.mvp.presenter.SettingsFragmentPresenter;
import com.moggot.commonalarmclock.mvp.presenter.SettingsFragmentPresenterImpl;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    @ScreenScope
    @Provides
    public MainFragmentPresenter provideMainFragmentPresenter() {
        return new MainFragmentPresenterImpl();
    }

    @ScreenScope
    @Provides
    public SettingsFragmentPresenter provideSettingsFragmentPresenter() {
        return new SettingsFragmentPresenterImpl();
    }
}
