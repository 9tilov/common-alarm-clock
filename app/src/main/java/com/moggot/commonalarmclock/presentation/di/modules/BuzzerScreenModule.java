package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.presenter.BuzzerActivityPresenter;
import com.moggot.commonalarmclock.mvp.presenter.BuzzerActivityPresenterImpl;
import com.moggot.commonalarmclock.mvp.view.BuzzerActivityView;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

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
