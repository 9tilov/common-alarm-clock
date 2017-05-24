package com.moggot.commonalarmclock.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.google.android.gms.analytics.Tracker;
import com.moggot.commonalarmclock.App;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.analytics.FirebaseAnalysis;

public class OnboardingActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tracker tracker = ((App) getApplication())
                .getDefaultTracker();
        tracker.enableAdvertisingIdCollection(true);

        FirebaseAnalysis firebaseAnalytics = new FirebaseAnalysis(this);
        firebaseAnalytics.init();

        SharedPreference.SaveTutorialStatus(this, false);
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen1));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen2));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen3));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen4));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen5));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen6));

        showSkipButton(false);
        setFlowAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onBackPressed() {
    }


}
