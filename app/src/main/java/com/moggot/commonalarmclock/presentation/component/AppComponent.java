package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.activity.MainActivity;
import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.modules.AppModule;
import com.moggot.commonalarmclock.presentation.modules.BuzzerScreenModule;
import com.moggot.commonalarmclock.presentation.modules.DataModule;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.modules.SettingsScreenModule;
import com.moggot.commonalarmclock.presentation.modules.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UtilsModule.class, AlarmModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    MainScreenComponent plus(MainScreenModule mainScreenModule);

    SettingsScreenComponent plus(SettingsScreenModule settingsScreenModule);

    BuzzerScreenModule plus(BuzzerScreenModule buzzerScreenModule);
}
