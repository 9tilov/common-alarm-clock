package com.moggot.commonalarmclock;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.alarm.AlarmDao;
import com.moggot.commonalarmclock.alarm.DaoSession;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DataBase {

    private Context applicationContext;
    private AlarmDao alarmDao;

    public DataBase(Context applicationContext) {
        this.applicationContext = applicationContext;
        alarmDao = setupDb();
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

    public List<Alarm> getAllAlarms() {
        return alarmDao.queryBuilder().orderAsc(AlarmDao.Properties.Date).build().list();
    }

    private AlarmDao setupDb() {
        DaoSession masterSession = ((App) applicationContext).getDaoSession();
        return masterSession.getAlarmDao();
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
}
