package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.SnoozeAlarm;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.observer.AlarmData;
import com.moggot.commonalarmclock.observer.AlarmGetUpDisplay;

import java.util.Calendar;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentSnooze extends Fragment {

    private Alarm alarm;

    public FragmentSnooze() {
    }

    public static FragmentSnooze newInstance(long id) {
        FragmentSnooze fragmentSnooze = new FragmentSnooze();
        Bundle args = new Bundle();
        args.putLong(Consts.EXTRA_ID, id);
        fragmentSnooze.setArguments(args);
        return fragmentSnooze;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = 0;
        if (getArguments() != null)
            id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity());
        alarm = db.getAlarm(id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_snooze, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        Button btnSnooze = (Button) view.findViewById(R.id.btnSnooze);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        AlarmData alarmData = new AlarmData();
        AlarmGetUpDisplay adapterDisplay = new AlarmGetUpDisplay(view, alarmData);
        alarmData.setAlarm(alarm);

        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SnoozeAlarm snoozeAlarm = new SnoozeAlarm(getActivity());
                Calendar calendar = Calendar.getInstance();
                snoozeAlarm.setAlarm(alarm, calendar.getTimeInMillis());
                getActivity().finish();
            }
        });
    }

}
