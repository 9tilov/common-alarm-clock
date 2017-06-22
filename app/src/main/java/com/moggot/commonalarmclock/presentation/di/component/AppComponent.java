package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.mvp.view.activity.MainActivity;
import com.moggot.commonalarmclock.mvp.view.activity.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.modules.AppModule;
import com.moggot.commonalarmclock.presentation.modules.DataModule;
import com.moggot.commonalarmclock.presentation.modules.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UtilsModule.class})
public interface AppComponent {

    void inject(OnboardingActivity activity);

    void inject(MainActivity activity);

    AlarmComponent plus(AlarmModule alarmModule);
}
