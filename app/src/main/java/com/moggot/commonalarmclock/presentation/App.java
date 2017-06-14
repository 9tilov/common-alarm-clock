package com.moggot.commonalarmclock.presentation;

import android.app.Application;

import com.moggot.commonalarmclock.presentation.component.AppComponent;
import com.moggot.commonalarmclock.presentation.component.DaggerAppComponent;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
