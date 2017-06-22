package com.moggot.commonalarmclock.presentation;

import android.app.Application;

import com.moggot.commonalarmclock.presentation.component.AppComponent;
import com.moggot.commonalarmclock.presentation.component.DaggerAppComponent;
import com.moggot.commonalarmclock.presentation.modules.AppModule;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
