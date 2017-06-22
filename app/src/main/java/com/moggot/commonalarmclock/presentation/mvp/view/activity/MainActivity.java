package com.moggot.commonalarmclock.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.data.SharedPreference;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.mvp.common.BaseActivity;
import com.moggot.commonalarmclock.mvp.view.activity.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.mvp.view.fragments.MainFragment;
import com.moggot.commonalarmclock.presentation.component.AppComponent;
import com.moggot.commonalarmclock.presentation.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();

        createFragment();
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

    private void startOnboarding() {
        if (sharedPreference.loadTutorialStatus()) {
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);
        }
    }

    private void createFragment() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.root_frame);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            ActivityUtils.addFragmentToActivity(fragmentManager, mainFragment, R.id.root_frame);
        }
    }
}