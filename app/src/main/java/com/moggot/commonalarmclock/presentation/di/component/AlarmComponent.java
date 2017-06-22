package com.moggot.commonalarmclock.presentation.component;

import com.moggot.commonalarmclock.mvp.presenter.MainFragmentPresenterImpl;
import com.moggot.commonalarmclock.mvp.presenter.SettingsFragmentPresenter;
import com.moggot.commonalarmclock.mvp.presenter.SettingsFragmentPresenterImpl;
import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.modules.AlarmScreenModule;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.scopes.AlarmScope;

import dagger.Subcomponent;

@AlarmScope
@Subcomponent(modules = AlarmModule.class)
public interface AlarmComponent {

    void inject(MainFragmentPresenterImpl presenter);

    void inject(SettingsFragmentPresenterImpl presenter);

    MainScreenComponent plus(MainScreenModule mainScreenModule);

    AlarmScreenComponent plus(AlarmScreenModule alarmScreenModule);
}
