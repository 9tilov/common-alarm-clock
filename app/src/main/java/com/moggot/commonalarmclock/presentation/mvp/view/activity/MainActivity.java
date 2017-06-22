package com.moggot.commonalarmclock.presentation.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.android.debug.hv.ViewServer;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.data.SharedPreference;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.presentation.di.component.AppComponent;
import com.moggot.commonalarmclock.presentation.mvp.common.BaseActivity;
import com.moggot.commonalarmclock.presentation.mvp.view.activity.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.MainFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();
        if (savedInstanceState == null)
            createFragment();

        ViewServer.get(this).addWindow(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
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
        MainFragment mainFragment = MainFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        ActivityUtils.addFragmentToActivity(fragmentManager, mainFragment, R.id.root_frame);
    }
}