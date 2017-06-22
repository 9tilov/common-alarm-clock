package com.moggot.commonalarmclock.presentation.di.modules;

import com.moggot.commonalarmclock.presentation.di.scopes.ScreenScope;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenter;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenterImpl;
import com.moggot.commonalarmclock.presentation.mvp.view.BuzzerActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class BuzzerScreenModule {

    private BuzzerActivityView buzzerActivityView;

    public BuzzerScreenModule(BuzzerActivityView buzzerActivityView) {
        this.buzzerActivityView = buzzerActivityView;
    }

    @ScreenScope
    @Provides
    public BuzzerActivityView provideBuzzerView() {
        return buzzerActivityView;
    }

    @ScreenScope
    @Provides
    public BuzzerActivityPresenter provideBuzzerPresenter() {
        return new BuzzerActivityPresenterImpl(buzzerActivityView);
    }
}
