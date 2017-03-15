package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.MainActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_snooze, container, false);
        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        Button btnSnooze = (Button) view.findViewById(R.id.btnSnooze);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final long id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity());
        final Alarm alarm = db.getAlarm(id);

        AlarmData alarmData = new AlarmData();
        AlarmGetUpDisplay adapterDisplay = new AlarmGetUpDisplay(view, alarmData);
        alarmData.setAlarm(alarm);
        adapterDisplay.display();

        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SnoozeAlarm snoozeAlarm = new SnoozeAlarm(getActivity());
                Calendar calendar = Calendar.getInstance();
                snoozeAlarm.setAlarm(alarm, calendar.getTimeInMillis());
                finish();
            }
        });
        return view;
    }

    public void finish() {
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);
        getActivity().finish();
    }
}
