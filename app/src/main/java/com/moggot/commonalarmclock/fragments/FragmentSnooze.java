package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.R;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentSnooze extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_snooze, container, false);
    }
}
