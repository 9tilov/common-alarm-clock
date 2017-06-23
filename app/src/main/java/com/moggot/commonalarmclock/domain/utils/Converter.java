package com.moggot.commonalarmclock.domain.utils;

import android.content.res.Resources;
import android.util.SparseIntArray;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

import java.util.Calendar;

public class Converter {

    public static String getDaysAsString(Resources res, SparseIntArray ids) {
        StringBuilder days = new StringBuilder();
        for (int i = 0; i < ids.size(); ++i) {
            String day = dayByteToString(res, ids.keyAt(i));
            days.append(day);
            if (i < ids.size() - 1)
                days.append(" ");
        }
        return days.toString();
    }

    private static String dayByteToString(Resources res, int day) {
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

    public static String getTimeAsString(long timeInMillis) {
        Calendar calendar = createCalendar(timeInMillis);
        return getHoursAsString(calendar) + ":" + getMinutesAsString(calendar);
    }

    private static Calendar createCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    private static String getHoursAsString(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String hourStr = String.valueOf(hour);
        if (hour < 10)
            return "0" + hourStr;
        else
            return hourStr;
    }

    private static String getMinutesAsString(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        String minuteStr = String.valueOf(minute);
        if (minute < 10)
            return "0" + minuteStr;
        else
            return minuteStr;
    }
}