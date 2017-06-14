package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.modules.BuzzerScreenModule;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.modules.SettingsScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.AlarmScope;

import dagger.Subcomponent;

@AlarmScope
@Subcomponent(modules = {AlarmModule.class})
public interface AlarmComponent {

    MainScreenComponent plus(MainScreenModule mainScreenModule);

    SettingsScreenComponent plus(SettingsScreenModule settingsScreenModule);

    BuzzerScreenModule plus(BuzzerScreenModule buzzerScreenModule);
}
