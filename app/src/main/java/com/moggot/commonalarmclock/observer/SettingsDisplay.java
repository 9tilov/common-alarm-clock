package com.moggot.commonalarmclock.observer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by toor on 22.02.17.
 */

public class SettingsDisplay implements Observer {

    private static final String LOG_TAG = "SettingsDisplay";

    private Alarm alarm;

    private Activity activity;

    private TextView tvAlarmTime;
    private SparseIntArray tbDaysOfWeek;
    private CheckBox checkBoxSnooze;
    private CheckBox checkBoxMathExample;
    private EditText etName;
    private RadioGroup rgMusicType;

    public SettingsDisplay(Context context, AlarmData alarmData) {
        this.activity = (Activity) context;

        tvAlarmTime = (TextView) activity.findViewById(R.id.tvAlarmTime);

        tbDaysOfWeek = new SparseIntArray();
        tbDaysOfWeek.put(R.id.tbMonday, Calendar.MONDAY);
        tbDaysOfWeek.put(R.id.tbTuesday, Calendar.TUESDAY);
        tbDaysOfWeek.put(R.id.tbWednesday, Calendar.WEDNESDAY);
        tbDaysOfWeek.put(R.id.tbThursday, Calendar.THURSDAY);
        tbDaysOfWeek.put(R.id.tbFriday, Calendar.FRIDAY);
        tbDaysOfWeek.put(R.id.tbSaturday, Calendar.SATURDAY);
        tbDaysOfWeek.put(R.id.tbSunday, Calendar.SUNDAY);

        checkBoxSnooze = (CheckBox) activity.findViewById(R.id.checkBoxSnooze);
        checkBoxMathExample = (CheckBox) activity.findViewById(R.id.checkBoxMath);

        etName = (EditText) activity.findViewById(R.id.etAlarmName);

        rgMusicType = (RadioGroup) activity.findViewById(R.id.rgMusicType);

        alarmData.registerObserver(this);
    }

    @Override
    public void update(Alarm alarm) {
        this.alarm = alarm;
        display();
    }

    public void display() {
        displayTime();
        displayDays();
        displayCheckBoxes();
        displayAlarmName();
        displayMusicType();
    }

    private void displayTime() {
        Date date = alarm.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        tvAlarmTime.setText(getTimeStr(calendar.getTimeInMillis()));
    }

    private void displayDays() {
        SparseIntArray ids = alarm.getIDs();
        for (int requestCode = 0; requestCode < ids.size(); ++requestCode) {
            for (int btnID = 0; btnID < tbDaysOfWeek.size(); ++btnID) {
                int key = tbDaysOfWeek.keyAt(btnID);
                if (ids.keyAt(requestCode) == tbDaysOfWeek.get(key))
                    ((ToggleButton) activity.findViewById(key))
                            .setChecked(true);
            }
        }
    }

    private void displayCheckBoxes() {
        checkBoxSnooze.setChecked(alarm.getIsSnoozeEnable());
        checkBoxMathExample.setChecked(alarm.getIsMathEnable());
    }

    private void displayAlarmName() {
        etName.setText(alarm.getName());
    }

    private void displayMusicType() {
        ((RadioButton) rgMusicType.getChildAt(alarm.getMusicType())).setChecked(true);
    }

    private String getTimeStr(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String timeBuilder = "";
        timeBuilder += pad(hour);
        timeBuilder += ":";
        timeBuilder += pad(minute);
        return timeBuilder;
    }

    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
