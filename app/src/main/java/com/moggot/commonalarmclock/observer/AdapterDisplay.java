package com.moggot.commonalarmclock.observer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.Conversion;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.Calendar;

/**
 * Created by toor on 22.02.17.
 */

public class AdapterDisplay implements Observer {

    private final static String LOG_TAG = "AdapterDisplay";

    private Alarm alarm;
    private View view;
    private Context context;

    public AdapterDisplay(Context context, View view, AlarmData alarmData) {
        this.view = view;
        this.context = context;
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
        displayAlarmName();
        displayMusicType();
        displayAlarmType();
        displayAlarmStatus();
    }

    private void displayTime() {
        ((TextView) view.findViewById(R.id.tvAdapterTime)).setText(getTimeAsString());
    }

    private void displayDays() {
        ((TextView) view.findViewById(R.id.tvAdapterDays)).setText(Conversion.getDaysAsString(context, alarm.getRepeatAlarmIDs()));
    }


    private void displayAlarmName() {
        ((TextView) view.findViewById(R.id.tvAdapterName)).setText(alarm.getName());
    }

    private void displayMusicType() {
        view.findViewById(R.id.ivAdapterMusicType).setVisibility(View.VISIBLE);

        switch (alarm.getMusicTypeEnum()) {
            case RADIO:
                ((ImageView) view.findViewById(R.id.ivAdapterMusicType)).setImageResource(R.drawable.ic_radio);
                break;
            case DEFAULT_RINGTONE:
                ((ImageView) view.findViewById(R.id.ivAdapterMusicType)).setImageResource(R.drawable.ic_note);
                break;
            case MUSIC_FILE:
                ((ImageView) view.findViewById(R.id.ivAdapterMusicType)).setImageResource(R.drawable.ic_music_file);
                break;
        }
    }

    private void displayAlarmStatus() {
        ((ToggleButton) view.findViewById(R.id.tgAdapterState)).setChecked(alarm.getState());
    }

    private void displayAlarmType() {
        if (alarm.getIsSnoozeEnable()) {
            view.findViewById(R.id.ivAdapterSnooze).setVisibility(View.VISIBLE);
            ((ImageView) view.findViewById(R.id.ivAdapterSnooze)).setImageResource(R.drawable.ic_snooze);
        } else
            view.findViewById(R.id.ivAdapterSnooze).setVisibility(View.GONE);
        if (alarm.getIsMathEnable()) {
            view.findViewById(R.id.ivAdapterMath).setVisibility(View.VISIBLE);
            ((ImageView) view.findViewById(R.id.ivAdapterMath)).setImageResource(R.drawable.ic_function);
        } else
            view.findViewById(R.id.ivAdapterMath).setVisibility(View.GONE);
    }

    private String getTimeAsString() {
        long timeInMillis = alarm.getTimeInMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String hourStr;
        if (hour < 10)
            hourStr = "0" + String.valueOf(hour);
        else
            hourStr = String.valueOf(hour);
        String minuteStr;
        if (minute < 10)
            minuteStr = "0" + String.valueOf(minute);
        else
            minuteStr = String.valueOf(minute);
        return hourStr + ":" + minuteStr;
    }
}
