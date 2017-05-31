package com.moggot.commonalarmclock;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.moggot.commonalarmclock.alarm.DaoMaster;
import com.moggot.commonalarmclock.alarm.DaoSession;

public class App extends Application {

    private DaoSession daoSession;

    private static final String DB_NAME = "alarm_db";
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * Получает счетчик {@link Tracker}, используемый по умолчанию для этого приложения {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}
