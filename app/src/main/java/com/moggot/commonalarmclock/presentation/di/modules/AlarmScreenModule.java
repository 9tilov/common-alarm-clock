package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.presenter.BuzzerActivityPresenter;
import com.moggot.commonalarmclock.mvp.presenter.BuzzerActivityPresenterImpl;
import com.moggot.commonalarmclock.mvp.view.BuzzerActivityView;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

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
