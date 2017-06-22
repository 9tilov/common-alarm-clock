package com.moggot.commonalarmclock.presentation.modules;

import android.content.Context;

import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.domain.utils.NetworkConnectionChecker;
import com.moggot.commonalarmclock.music.MusicPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    public NetworkConnectionChecker provideNetworkConnectionChecker(Context context) {
        return new NetworkConnectionChecker(context);
    }

    @Provides
    @Singleton
    public Analysis provideAnalysis(Context context) {
        return new Analysis(context);
    }
}