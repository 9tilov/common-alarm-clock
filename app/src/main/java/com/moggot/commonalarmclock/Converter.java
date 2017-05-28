package com.moggot.commonalarmclock;

import android.content.res.Resources;
import android.util.SparseIntArray;

import java.util.Calendar;

public class Converter {

    private Resources res;

    public Converter(Resources res) {
        this.res = res;
    }

    public String getDaysAsString(SparseIntArray ids) {
        StringBuilder days = new StringBuilder();
        for (int i = 0; i < ids.size(); ++i) {
            String day = dayByteToString(ids.keyAt(i));
            days.append(day);
            if (i < ids.size() - 1)
                days.append(" ");
        }
        return days.toString();
    }

    private String dayByteToString(int day) {
        switch (day) {
            case Consts.TOMORROW:
                return res.getString(R.string.tomorrow);
            case Calendar.MONDAY:
                return res.getString(R.string.monday_short);
            case Calendar.TUESDAY:
                return res.getString(R.string.tuesday_short);
            case Calendar.WEDNESDAY:
                return res.getString(R.string.wednesday_short);
            case Calendar.THURSDAY:
                return res.getString(R.string.thursday_short);
            case Calendar.FRIDAY:
                return res.getString(R.string.friday_short);
            case Calendar.SATURDAY:
                return res.getString(R.string.saturday_short);
            case Calendar.SUNDAY:
                return res.getString(R.string.sunday_short);
            default:
                return "";
        }
    }

    public String getTimeAsString(long timeInMillis) {
        Calendar calendar = createCalendar(timeInMillis);
        return getHoursAsString(calendar) + ":" + getMinutesAsString(calendar);
    }

    private Calendar createCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    private String getHoursAsString(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String hourStr;
        if (hour < 10)
            hourStr = "0" + String.valueOf(hour);
        else
            hourStr = String.valueOf(hour);
        return hourStr;
    }

    private String getMinutesAsString(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        String minuteStr;
        if (minute < 10)
            minuteStr = "0" + String.valueOf(minute);
        else
            minuteStr = String.valueOf(minute);
        return minuteStr;
    }
}