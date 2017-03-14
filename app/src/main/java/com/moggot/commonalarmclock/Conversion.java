package com.moggot.commonalarmclock;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.Calendar;

/**
 * Created by toor on 09.03.17.
 */

public class Conversion {

    private static final String LOG_TAG = "Conversion";

    static public String getDaysAsString(Context context, SparseIntArray ids) {
        SparseArray<String> byteToString = new SparseArray<>();
        byteToString.put(Consts.TOMORROW, context.getString(R.string.tomorrow));
        byteToString.put(Calendar.MONDAY, context.getString(R.string.monday_short));
        byteToString.put(Calendar.TUESDAY, context.getString(R.string.tuesday_short));
        byteToString.put(Calendar.WEDNESDAY, context.getString(R.string.wednesday_short));
        byteToString.put(Calendar.THURSDAY, context.getString(R.string.thursday_short));
        byteToString.put(Calendar.FRIDAY, context.getString(R.string.friday_short));
        byteToString.put(Calendar.SATURDAY, context.getString(R.string.saturday_short));
        byteToString.put(Calendar.SUNDAY, context.getString(R.string.sunday_short));

        String days = "";

        for (int i = 0; i < ids.size(); ++i) {
            if (i == ids.size() - 1)
                days += byteToString.get(ids.keyAt(i));
            else
                days += byteToString.get(ids.keyAt(i)) + " ";
        }
        return days;
    }
}