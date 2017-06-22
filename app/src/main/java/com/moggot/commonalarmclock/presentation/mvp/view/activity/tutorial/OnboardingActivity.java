package com.moggot.commonalarmclock.mvp.view.activity.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.data.SharedPreference;
import com.moggot.commonalarmclock.presentation.App;

import javax.inject.Inject;

public class OnboardingActivity extends AppIntro2 {

    @Inject
    Analysis analysis;

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getAppComponent().inject(this);

        analysis.sendScreenName(this.getClass().getSimpleName());
        sharedPreference.saveTutorialStatus(false);

        addSlides();
        showSkipButton(false);
        setFlowAnimation();
    }

    private void addSlides() {
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen1));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen2));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen3));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen4));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen5));
        addSlide(OnboardingFragment.newInstance(R.layout.onboarding_screen6));
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
