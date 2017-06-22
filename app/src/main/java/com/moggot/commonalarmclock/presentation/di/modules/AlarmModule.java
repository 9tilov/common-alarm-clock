package com.moggot.commonalarmclock.presentation.modules;

import android.content.Context;

import com.moggot.commonalarmclock.music.MusicPlayer;
import com.moggot.commonalarmclock.presentation.scopes.AlarmScope;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;

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
