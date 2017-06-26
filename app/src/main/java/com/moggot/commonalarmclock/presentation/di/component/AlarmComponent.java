package com.moggot.commonalarmclock.presentation.di.component;

import com.moggot.commonalarmclock.domain.music.MusicService;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmScreenModule;
import com.moggot.commonalarmclock.presentation.di.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.di.scopes.AlarmScope;
import com.moggot.commonalarmclock.presentation.mvp.presenter.MainFragmentPresenterImpl;
import com.moggot.commonalarmclock.presentation.mvp.presenter.SettingsFragmentPresenterImpl;

import dagger.Subcomponent;

@AlarmScope
@Subcomponent(modules = AlarmModule.class)
public interface AlarmComponent {

    void inject(MainFragmentPresenterImpl presenter);

    void inject(SettingsFragmentPresenterImpl presenter);

    void inject(MusicService musicService);

    MainScreenComponent plus(MainScreenModule mainScreenModule);

    AlarmScreenComponent plus(AlarmScreenModule alarmScreenModule);
}
