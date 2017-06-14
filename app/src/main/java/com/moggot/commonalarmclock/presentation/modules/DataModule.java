package com.moggot.commonalarmclock.presentation.modules;

import android.content.Context;

import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.data.SharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    public DataBase provideDataBase(Context context) {
        return new DataBase(context);
    }

    @Provides
    @Singleton
    public SharedPreference providePreferences(Context context) {
        return new SharedPreference(context);
    }
}
