package com.moggot.commonalarmclock.observer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 13.03.17.
 */

public class AlarmGetUpDisplay implements Observer {

    private Alarm alarm;
    private View view;

    public AlarmGetUpDisplay(View view, AlarmData alarmData) {
        this.view = view;
        alarmData.registerObserver(this);
    }

    @Override
    public void update(Alarm alarm) {
        this.alarm = alarm;
        display();
    }

    public void display() {
        displayName();
    }

    private void displayName() {
        ((TextView) view.findViewById(R.id.tvName)).setText(alarm.getName());
    }
}
