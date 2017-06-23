package com.moggot.commonalarmclock.domain.analytics;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

public class Analysis {

    private Context appContext;
    private Tracker tracker;

    public Analysis(Context appContext) {
        this.appContext = appContext;

        start();
    }

    private void start() {
        analyticsInit();
        firebaseInit();
    }

    private void analyticsInit() {
        this.tracker = createDefaultTracker();
    }

    /**
     * Получает счетчик {@link Tracker}, используемый по умолчанию для этого приложения {@link Application}.
     *
     * @return tracker
     */
    synchronized private Tracker createDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(appContext);
            tracker = analytics.newTracker(R.xml.app_tracker);
        }
        return tracker;
    }

    public void sendScreenName(String name) {
        if (tracker != null) {
            tracker.setScreenName(name);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private void firebaseInit() {
        FirebaseAnalytics firebaseAnalytics;
        if (!(ActivityCompat.checkSelfPermission(appContext, android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED))
            return;
        firebaseAnalytics = FirebaseAnalytics.getInstance(appContext);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Consts.FIREBASE_ITEM_ID);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Consts.FIREBASE_ITEM_NAME);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, Consts.FIREBASE_CONTENT_TYPE);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
