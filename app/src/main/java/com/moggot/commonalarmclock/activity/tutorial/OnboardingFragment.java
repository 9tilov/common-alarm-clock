package com.moggot.commonalarmclock.activity.tutorial;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.R;

import java.util.Locale;

public class OnboardingFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static OnboardingFragment newInstance(int layoutResId) {
        OnboardingFragment sampleSlide = new OnboardingFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    private int layoutResId;

    public OnboardingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
        String sDefSystemLanguage = locale.getLanguage();
        if (sDefSystemLanguage.equals("ru")) {
            switch (layoutResId) {
                case R.layout.onboarding_screen2:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_time_ru));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_time_ru, null));
                    }
                    break;
                case R.layout.onboarding_screen3:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_days_ru));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_days_ru, null));
                    }
                    break;
                case R.layout.onboarding_screen4:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_checkbox_ru));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_checkbox_ru, null));
                    }
                    break;
                case R.layout.onboarding_screen5:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_music_ru));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_music_ru, null));
                    }
                    break;
                case R.layout.onboarding_screen6:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_save_ru));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_save_ru, null));
                    }
                    break;
                default:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.onboarding_main));
                    } else {
                        view.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.onboarding_main, null));
                    }
                    break;
            }
        }
        return view;
    }
}

