package com.moggot.commonalarmclock.presentation.di.modules;

import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.presenter.MainFragmentPresenter;
import com.moggot.commonalarmclock.presentation.mvp.presenter.MainFragmentPresenterImpl;
import com.moggot.commonalarmclock.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.moggot.commonalarmclock.presentation.mvp.presenter.SettingsFragmentPresenterImpl;

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
