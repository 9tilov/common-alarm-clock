package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.main.MainPresenter;
import com.moggot.commonalarmclock.mvp.main.MainPresenterImpl;
import com.moggot.commonalarmclock.mvp.main.MainView;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    private MainView mainView;

    public MainScreenModule(MainView mainView) {
        this.mainView = mainView;
    }

    @ScreenScope
    @Provides
    public MainView provideMainView() {
        return mainView;
    }

    @ScreenScope
    @Provides
    public MainPresenter provideMainPresenter() {
        return new MainPresenterImpl(mainView);
    }
}
