package com.moggot.commonalarmclock.presentation.di.modules;

import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenter;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenterImpl;
import com.moggot.commonalarmclock.presentation.mvp.view.BuzzerActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmScreenModule {

    private BuzzerActivityView view;

    public AlarmScreenModule(BuzzerActivityView view) {
        this.view = view;
    }

    @ScreenScope
    @Provides
    public BuzzerActivityView provideBuzzerActivityView() {
        return view;
    }

    @ScreenScope
    @Provides
    public BuzzerActivityPresenter provideBuzzerActivityPresenter() {
        return new BuzzerActivityPresenterImpl(view);
    }
}
