package com.moggot.commonalarmclock.presentation.di.modules;

import android.content.Context;

import com.moggot.commonalarmclock.domain.music.MusicPlayer;
import com.moggot.commonalarmclock.presentation.di.scopes.AlarmScope;
import com.moggot.commonalarmclock.domain.schedule.AlarmScheduler;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmModule {

    @AlarmScope
    @Provides
    public AlarmScheduler provideAlarmScheduler(Context context) {
        return new AlarmScheduler(context);
    }

    @AlarmScope
    @Provides
    public MusicPlayer provideMusicPlayer(Context context) {
        return new MusicPlayer(context);
    }
}
