package com.moggot.commonalarmclock.presentation.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context appContext) {
        this.context = appContext;
    }

    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }
}
