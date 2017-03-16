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
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.observer.AlarmData;
import com.moggot.commonalarmclock.observer.AlarmGetUpDisplay;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentCommon extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm_common, container, false);

        final long id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity());
        Alarm alarm = db.getAlarm(id);

        AlarmData alarmData = new AlarmData();
        AlarmGetUpDisplay adapterDisplay = new AlarmGetUpDisplay(view, alarmData);
        alarmData.setAlarm(alarm);
        adapterDisplay.display();

        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        return view;
    }

    public void finish() {
        getActivity().finish();
    }

}