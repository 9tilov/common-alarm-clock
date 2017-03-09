package com.moggot.commonalarmclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.alarm.AlarmDao;
import com.moggot.commonalarmclock.alarm.DaoMaster;
import com.moggot.commonalarmclock.alarm.DaoSession;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by toor on 01.03.17.
 */

public class DataBase {

    private Context ctx;
    private AlarmDao alarmDao;

    private final String DB_NAME = "alarm_db";

    public DataBase(Context ctx) {
        this.ctx = ctx;
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
        return alarmDao.queryBuilder().build().list();
    }

    private AlarmDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(ctx, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getAlarmDao();
    }

    public int getRandomRequestCode() {
        HashSet<Integer> unique = new HashSet<>();
        List<Alarm> alarms = getAllAlarms();
        for (Alarm alarm:alarms) {
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            List<Integer> ids = new Gson().fromJson(alarm.getRequestCodes(), type);
            for (Integer ID : ids) {
                unique.add(ID);
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
