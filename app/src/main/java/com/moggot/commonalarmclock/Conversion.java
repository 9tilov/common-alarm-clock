package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by toor on 09.03.17.
 */

public class Conversion {

    private static final String LOG_TAG = "Conversion";

    static List<String> getDaysList(Context ctx, int b_days) {
        List<String> days = new LinkedList<>();

        Map<Integer, Integer> ByteToString = new HashMap<>();
        ByteToString.put(0, R.string.tomorrow);
        ByteToString.put(1, R.string.monday_short);
        ByteToString.put(2, R.string.tuesday_short);
        ByteToString.put(3, R.string.wednesday_short);
        ByteToString.put(4, R.string.thursday_short);
        ByteToString.put(5, R.string.friday_short);
        ByteToString.put(6, R.string.saturday_short);
        ByteToString.put(7, R.string.sunday_short);
        Resources res = ctx.getResources();
        int mask = 1;
        for (int i = 0; i < 7; ++i) {
            if ((b_days & (mask << i)) != 0) {
                days.add(res.getString(ByteToString.get(i + 1)));
            }
        }
        if (days.isEmpty()) {
            days.add(res.getString(ByteToString.get(0)));
        }
        return days;
    }

    public static String getDaysAsString(Context ctx, int b_days) {
        List<String> days = getDaysList(ctx, b_days);
        Log.v(LOG_TAG, "days = " + days);
        String days_str = "";
        for (int i = 0; i < days.size(); ++i) {
            if (i == days.size() - 1) {
                days_str += days.get(i);
            } else
                days_str += days.get(i) + " ";
        }
        return days_str;
    }
}
