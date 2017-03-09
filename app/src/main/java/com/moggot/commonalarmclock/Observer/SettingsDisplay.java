package com.moggot.commonalarmclock.Observer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private TextView tvAlarmTime;
    private SparseArray<Byte> tbDaysOfWeek;
    private CheckBox checkBoxSnooze;
    private CheckBox checkBoxMathExample;
    private EditText etName;
    private RadioGroup rgMusicType;

    public SettingsDisplay(Context ctx, AlarmData alarmData) {
        this.activity = (Activity) ctx;

        tvAlarmTime = (TextView) activity.findViewById(R.id.tvAlarmTime);

        tbDaysOfWeek = new SparseArray<>();
        tbDaysOfWeek.put(R.id.tbMonday, Consts.DAYS.MONDAY.getCode());
        tbDaysOfWeek.put(R.id.tbTuesday, Consts.DAYS.TUESDAY.getCode());
        tbDaysOfWeek.put(R.id.tbWednesday, Consts.DAYS.WEDNESDAY.getCode());
        tbDaysOfWeek.put(R.id.tbThursday, Consts.DAYS.THURSDAY.getCode());
        tbDaysOfWeek.put(R.id.tbFriday, Consts.DAYS.FRIDAY.getCode());
        tbDaysOfWeek.put(R.id.tbSaturday, Consts.DAYS.SATURDAY.getCode());
        tbDaysOfWeek.put(R.id.tbSunday, Consts.DAYS.SUNDAY.getCode());

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
        Log.v(LOG_TAG, "days_settings = " + alarm.getDays());
        for (int i = 0; i < tbDaysOfWeek.size(); ++i) {
            int key = tbDaysOfWeek.keyAt(i);
            int temp = alarm.getDays() & tbDaysOfWeek.get(key);
            int t_temp = temp ^ tbDaysOfWeek.get(key);
            ((ToggleButton) activity.findViewById(key))
                    .setChecked((temp > 0 && t_temp == 0));
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
