package com.moggot.commonalarmclock.activity;

import android.content.Intent;
import android.os.Bundle;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.activity.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.data.SharedPreference;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.fragments.MainFragment;
import com.moggot.commonalarmclock.presentation.component.AppComponent;

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
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.root_frame);
        }
    }

}