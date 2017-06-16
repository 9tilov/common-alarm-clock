package com.moggot.commonalarmclock.presentation.modules;

import android.content.Context;

import com.moggot.commonalarmclock.schedule.AlarmScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmModule {

    @Singleton
    @Provides
    public AlarmScheduler provideAlarmScheduler(Context context) {
        return new AlarmScheduler(context);
    }
}
