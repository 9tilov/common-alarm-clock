package com.moggot.commonalarmclock.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 13.03.17.
 */

public class FragmentCreator {

    private Activity activity;

    public FragmentCreator(Activity activity) {
        this.activity = activity;
    }

    public void createFragment(Alarm alarm) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment fragment;
        if (alarm.getIsMathEnable()) {
            fragment = new FragmentMath();
        } else {
            if (alarm.getIsSnoozeEnable())
                fragment = new FragmentSnooze();
            else
                fragment = new FragmentCommon();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(Consts.EXTRA_ID, alarm.getId());
        fragment.setArguments(bundle);

        ft.add(R.id.frgmCont, fragment);
        ft.commit();
    }

    public void replaceFragment(Alarm alarm) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment fragment;
        if (alarm.getIsSnoozeEnable())
            fragment = new FragmentSnooze();
        else
            fragment = new FragmentCommon();

        Bundle bundle = new Bundle();
        bundle.putLong(Consts.EXTRA_ID, alarm.getId());
        fragment.setArguments(bundle);

        ft.replace(R.id.frgmCont, fragment);
        ft.commit();
    }
}
