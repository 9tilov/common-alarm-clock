package com.moggot.commonalarmclock.presentation.modules;

import android.content.Context;

import com.moggot.commonalarmclock.Log;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.domain.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    public NetworkManager provideNetworkManager(Context context) {
        return new NetworkManager(context);
    }

    @Provides
    @Singleton
    public Analysis provideAnalysis(Context context) {
        return new Analysis(context);
    }

    @Provides
    @Singleton
    public Log provideLogger() {
        return new Log();
    }
}
