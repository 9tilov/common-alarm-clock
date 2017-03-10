package com.moggot.commonalarmclock.observer;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 22.02.17.
 */

public interface Observer {

    void update(Alarm alarm);

}
