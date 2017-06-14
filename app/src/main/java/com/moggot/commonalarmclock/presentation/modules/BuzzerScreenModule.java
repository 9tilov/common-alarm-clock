package com.moggot.commonalarmclock.presentation.modules;

import com.moggot.commonalarmclock.mvp.buzzer.BuzzerPresenter;
import com.moggot.commonalarmclock.mvp.buzzer.BuzzerPresenterImpl;
import com.moggot.commonalarmclock.mvp.buzzer.BuzzerView;
import com.moggot.commonalarmclock.presentation.scopes.ScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class BuzzerScreenModule {

    private BuzzerView buzzerView;

    public BuzzerScreenModule(BuzzerView buzzerView) {
        this.buzzerView = buzzerView;
    }

    @ScreenScope
    @Provides
    public BuzzerView provideBuzzerView() {
        return buzzerView;
    }

    @ScreenScope
    @Provides
    public BuzzerPresenter provideBuzzerPresenter() {
        return new BuzzerPresenterImpl(buzzerView);
    }
}
