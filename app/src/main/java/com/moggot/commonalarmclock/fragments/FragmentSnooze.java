package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.commonalarmclock.AlarmContext;
import com.moggot.commonalarmclock.AlarmManager;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.observer.AlarmData;
import com.moggot.commonalarmclock.observer.AlarmGetUpDisplay;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentSnooze extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_snooze, container, false);
        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        Button btnSnooze = (Button) view.findViewById(R.id.btnSnooze);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        final long id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity());
        final Alarm alarm = db.getAlarm(id);

        AlarmData alarmData = new AlarmData();
        AlarmGetUpDisplay adapterDisplay = new AlarmGetUpDisplay(getActivity(), view, alarmData);
        alarmData.setAlarm(alarm);
        adapterDisplay.display();

        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmContext alarmContext = new AlarmContext(alarm, getActivity());
                AlarmManager alarmManager = new AlarmManager();
                alarmManager.setAlarm(alarmContext);
                getActivity().finish();
            }
        });
        return view;
    }
}
