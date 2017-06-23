package com.moggot.commonalarmclock.presentation.di.modules;

import android.content.Context;

import com.moggot.commonalarmclock.domain.schedule.AlarmScheduler;
import com.moggot.commonalarmclock.presentation.di.scopes.AlarmScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmModule {

    @AlarmScope
    @Provides
    public AlarmScheduler provideAlarmScheduler(Context context) {
        return new AlarmScheduler(context);
    }
}