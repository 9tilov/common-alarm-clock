package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by toor on 09.03.17.
 */

public class Conversion {

    private static final String LOG_TAG = "Conversion";

    static public String getDaysAsString(Context context, SparseIntArray ids) {
        SparseArray<String> byteToString = new SparseArray<>();
        byteToString.put(Consts.DAYS.TOMORROW.getCode(), context.getString(R.string.tomorrow));
        byteToString.put(Consts.DAYS.MONDAY.getCode(), context.getString(R.string.monday_short));
        byteToString.put(Consts.DAYS.TUESDAY.getCode(), context.getString(R.string.tuesday_short));
        byteToString.put(Consts.DAYS.WEDNESDAY.getCode(), context.getString(R.string.wednesday_short));
        byteToString.put(Consts.DAYS.THURSDAY.getCode(), context.getString(R.string.thursday_short));
        byteToString.put(Consts.DAYS.FRIDAY.getCode(), context.getString(R.string.friday_short));
        byteToString.put(Consts.DAYS.SATURDAY.getCode(), context.getString(R.string.saturday_short));
        byteToString.put(Consts.DAYS.SUNDAY.getCode(), context.getString(R.string.sunday_short));

        String days = "";

        for (int i = 0; i < ids.size(); ++i) {
            if (i == ids.size() - 1)
                days += byteToString.get(ids.keyAt(i));
            else
                days += byteToString.get(ids.keyAt(i)) + " ";
        }

        return days;
    }

//    static List<String> getDaysList(Context ctx, int b_days) {
//        List<String> days = new LinkedList<>();
//
//        Map<Integer, Integer> ByteToString = new HashMap<>();
//        ByteToString.put(0, R.string.tomorrow);
//        ByteToString.put(1, R.string.monday_short);
//        ByteToString.put(2, R.string.tuesday_short);
//        ByteToString.put(3, R.string.wednesday_short);
//        ByteToString.put(4, R.string.thursday_short);
//        ByteToString.put(5, R.string.friday_short);
//        ByteToString.put(6, R.string.saturday_short);
//        ByteToString.put(7, R.string.sunday_short);
//        Resources res = ctx.getResources();
//        int mask = 1;
//        for (int i = 0; i < 7; ++i) {
//            if ((b_days & (mask << i)) != 0) {
//                days.add(res.getString(ByteToString.get(i + 1)));
//            }
//        }
//        if (days.isEmpty()) {
//            days.add(res.getString(ByteToString.get(0)));
//        }
//        return days;
//    }
//
//    public static String getDaysAsString(Context ctx, int b_days) {
//        List<String> days = getDaysList(ctx, b_days);
//        String days_str = "";
//        for (int i = 0; i < days.size(); ++i) {
//            if (i == days.size() - 1) {
//                days_str += days.get(i);
//            } else
//                days_str += days.get(i) + " ";
//        }
//        return days_str;
//    }
}
