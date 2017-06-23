package com.moggot.commonalarmclock.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.commonalarmclock.data.alarm.Alarm;
import com.moggot.commonalarmclock.data.alarm.AlarmDao;
import com.moggot.commonalarmclock.data.alarm.DaoMaster;
import com.moggot.commonalarmclock.data.alarm.DaoSession;
import com.moggot.commonalarmclock.domain.utils.Log;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataBase {

    private Context applicationContext;
    private AlarmDao alarmDao;
    private static final String DB_NAME = "alarm_db";

    public DataBase(Context applicationContext) {
        this.applicationContext = applicationContext;
        alarmDao = setupDb();

        Log.v("createDB");
    }

    private AlarmDao setupDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(applicationContext, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession.getAlarmDao();
    }

    public void addAlarm(Alarm alarm) {
        alarmDao.insert(alarm);
    }

    public void editAlarm(Alarm alarm) {
        alarmDao.update(alarm);
    }

    public void deleteAlarm(Alarm alarm) {
        alarmDao.delete(alarm);
    }

    public Alarm getAlarm(long id) {
        return alarmDao.load(id);
    }

    public int getRandomRequestCode() {
        HashSet<Integer> unique = new HashSet<>();
        List<Alarm> alarms = getAllAlarms();
        for (Alarm alarm : alarms) {
            Type type = new TypeToken<SparseIntArray>() {
            }.getType();
            SparseIntArray ids = new Gson().fromJson(alarm.getRequestCodes(), type);
            for (int i = 0; i < ids.size(); ++i) {
                unique.add(ids.valueAt(i));
            }
        }
        Random rnd = new Random();
        int requestCode;
        do {
            requestCode = rnd.nextInt(Integer.MAX_VALUE);
        } while (unique.contains(requestCode));
        return requestCode;
    }

    public List<Alarm> getAllAlarms() {
        List<Alarm> alarms = alarmDao.queryBuilder().build().list();
        sortByTime(alarms);
        return alarms;
    }

    private void sortByTime(List<Alarm> alarms) {
        Collections.sort(alarms, new Comparator<Alarm>() {
            @Override
            public int compare(Alarm o1, Alarm o2) {
                DateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
                String time1 = localDateFormat.format(o1.getDate());
                String time2 = localDateFormat.format(o2.getDate());
                return time1.compareTo(time2);
            }
        });
    }

    public List<Alarm> getAllActiveAlarms() {
        return alarmDao.queryBuilder().where(AlarmDao.Properties.State.eq(true)).build().list();
    }
}
