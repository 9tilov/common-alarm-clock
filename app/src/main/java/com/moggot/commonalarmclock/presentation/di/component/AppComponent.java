package com.moggot.commonalarmclock.presentation.di.component;

import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.di.modules.AppModule;
import com.moggot.commonalarmclock.presentation.di.modules.DataModule;
import com.moggot.commonalarmclock.presentation.di.modules.UtilsModule;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.MainActivity;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.tutorial.OnboardingActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UtilsModule.class})
public interface AppComponent {

    void inject(OnboardingActivity activity);

    void inject(MainActivity activity);

    AlarmComponent plus(AlarmModule alarmModule);
}
