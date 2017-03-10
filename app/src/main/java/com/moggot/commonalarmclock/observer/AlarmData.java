package com.moggot.commonalarmclock.Observer;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by toor on 22.02.17.
 */

public class AlarmData implements Observable {

    private List<Observer> observers;

    private Alarm alarm;

    public AlarmData() {
        observers = new LinkedList<>();
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(alarm);
        }
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
        notifyObservers();
    }
}
