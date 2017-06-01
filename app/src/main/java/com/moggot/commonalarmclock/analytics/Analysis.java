package com.moggot.commonalarmclock.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moggot.commonalarmclock.App;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

public class Analysis {

    private Context context;
    private Tracker tracker;

    public Analysis(Context context) {
        this.context = context;
    }

    public void start() {
        analyticsInit();
        firebaseInit();
    }

    private void analyticsInit() {
        this.tracker = ((App) ((Activity) context).getApplication())
                .getDefaultTracker();
    }

    public void sendScreenName(String name) {
        if (tracker != null) {
            tracker.setScreenName(name);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private void firebaseInit() {
        FirebaseAnalytics firebaseAnalytics;
        if (!(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED))
            return;
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Consts.FIREBASE_ITEM_ID);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Consts.FIREBASE_ITEM_NAME);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, Consts.FIREBASE_CONTENT_TYPE);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
