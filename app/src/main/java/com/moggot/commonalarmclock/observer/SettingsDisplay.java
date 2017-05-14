package com.moggot.commonalarmclock.observer;

import android.app.Activity;
import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.Consts;
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

    private SparseIntArray tbDaysOfWeek;

    public SettingsDisplay(Context context, AlarmData alarmData) {
        this.activity = (Activity) context;

        tbDaysOfWeek = new SparseIntArray();
        tbDaysOfWeek.put(R.id.tbMonday, Calendar.MONDAY);
        tbDaysOfWeek.put(R.id.tbTuesday, Calendar.TUESDAY);
        tbDaysOfWeek.put(R.id.tbWednesday, Calendar.WEDNESDAY);
        tbDaysOfWeek.put(R.id.tbThursday, Calendar.THURSDAY);
        tbDaysOfWeek.put(R.id.tbFriday, Calendar.FRIDAY);
        tbDaysOfWeek.put(R.id.tbSaturday, Calendar.SATURDAY);
        tbDaysOfWeek.put(R.id.tbSunday, Calendar.SUNDAY);

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
        ((TextView) activity.findViewById(R.id.tvAlarmTime)).setText(getTimeStr(calendar.getTimeInMillis()));
    }

    private void displayCheckBoxDays() {
        if (alarm.getRepeatAlarmIDs().get(Consts.TOMORROW) > 0)
            ((CheckBox) activity.findViewById(R.id.checkBoxRepeat)).setChecked(false);
        else
            ((CheckBox) activity.findViewById(R.id.checkBoxRepeat)).setChecked(true);
    }

    private void displayDays() {
        displayCheckBoxDays();
        if (((CheckBox) activity.findViewById(R.id.checkBoxRepeat)).isChecked()) {
            ((RelativeLayout) activity.findViewById(R.id.rlDays)).setVisibility(View.VISIBLE);
            SparseIntArray ids = alarm.getRepeatAlarmIDs();
            for (int requestCode = 0; requestCode < ids.size(); ++requestCode) {
                for (int btnID = 0; btnID < tbDaysOfWeek.size(); ++btnID) {
                    int key = tbDaysOfWeek.keyAt(btnID);
                    if (ids.keyAt(requestCode) == tbDaysOfWeek.get(key))
                        ((ToggleButton) activity.findViewById(key))
                                .setChecked(true);
                }
            }
        } else {
            ((RelativeLayout) activity.findViewById(R.id.rlDays)).setVisibility(View.GONE);
        }
    }

    private void displayCheckBoxes() {
        ((CheckBox) activity.findViewById(R.id.checkBoxSnooze)).setChecked(alarm.getIsSnoozeEnable());
        ((CheckBox) activity.findViewById(R.id.checkBoxMath)).setChecked(alarm.getIsMathEnable());
    }

    private void displayAlarmName() {
        ((EditText) activity.findViewById(R.id.etAlarmName)).setText(alarm.getName());
    }

    private void displayMusicType() {
        ((RadioButton) ((RadioGroup) activity.findViewById(R.id.rgMusicType)).getChildAt(alarm.getMusicType())).setChecked(true);
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
