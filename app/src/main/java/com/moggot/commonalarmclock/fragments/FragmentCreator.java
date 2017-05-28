package com.moggot.commonalarmclock.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

public class FragmentCreator {

    private Activity activity;

    public FragmentCreator(Activity activity) {
        this.activity = activity;
    }

    public void createFragment(Alarm alarm) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment fragment;
        if (alarm.getIsMathEnable()) {
            fragment = FragmentMath.newInstance(alarm.getId());
        } else {
            if (alarm.getIsSnoozeEnable())
                fragment = FragmentSnooze.newInstance(alarm.getId());
            else
                fragment = FragmentCommon.newInstance(alarm.getId());
        }

        ft.add(R.id.frgmCont, fragment);
        ft.commit();
    }

    public void replaceFragment(Alarm alarm) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment fragment;
        if (alarm.getIsSnoozeEnable())
            fragment = FragmentSnooze.newInstance(alarm.getId());
        else
            fragment = FragmentCommon.newInstance(alarm.getId());

        ft.replace(R.id.frgmCont, fragment);
        ft.commit();
    }
}
