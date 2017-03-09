package com.moggot.commonalarmclock;

import android.content.Context;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 01.03.17.
 */

public class AlarmContext {

    private AlarmState state;
    private Alarm alarm;
    private Context context;

    public AlarmContext(Alarm alarm, Context context) {
        this.state = null;
        this.alarm = alarm;
        this.context = context;
    }

    public Alarm getAlarm() {
        return this.alarm;
    }

    public Context getActivityContext() {
        return this.context;
    }

    public void setState(AlarmState state) {
        this.state = state;
    }

    public AlarmState getState() {
        return this.state;
    }

}
