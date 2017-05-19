package com.moggot.commonalarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.google.android.gms.analytics.Tracker;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.analytics.AnalyticsApplication;
import com.moggot.commonalarmclock.analytics.FirebaseAnalysis;
import com.moggot.commonalarmclock.animation.AddAlarmAnimationBounce;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.tutorial.SharedPreference;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemTouchListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private List<Alarm> alarms;
    private DataBase db;
    private SwipeRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();
        initAnalytics();

        this.db = new DataBase(this);
        alarms = db.getAllAlarms();
        Log.v(LOG_TAG, "size1 = " + alarms.size());
//        Log.v(LOG_TAG, "time1 = " + alarms.get(1).getDate());
        this.adapter = new SwipeRecyclerViewAdapter(this, alarms, this);

        recyclerView = (RecyclerView) findViewById(R.id.langRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onCardViewTap(View view, int position) {
        Alarm alarm = adapter.getAlarmAtPosition(position);
        Intent intent = new Intent(this, ActivitySettings.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
    }

    @Override
    public void onStateChange(View view, int position) {
        Alarm alarm = adapter.getAlarmAtPosition(position);
        alarm.setState(!alarm.getState());
        db.editAlarm(alarm);
    }

    private void startOnboarding() {
        if (SharedPreference.LoadTutorialStatus(this)) {
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);
        }
    }

    private void initAnalytics() {
        Tracker tracker = ((AnalyticsApplication) getApplication())
                .getDefaultTracker();
        tracker.enableAdvertisingIdCollection(true);

        FirebaseAnalysis firebaseAnalytics = new FirebaseAnalysis(this);
        firebaseAnalytics.init();
    }

    public void onClickAdd(View view) {
        AnimationBounce animation = new AddAlarmAnimationBounce(this);
        animation.animate(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_SETTINGS:
                DataBase db = new DataBase(this);
                alarms = db.getAllAlarms();
                this.adapter = new SwipeRecyclerViewAdapter(this, alarms, this);
                recyclerView = (RecyclerView) findViewById(R.id.langRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, adapter);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
//                adapter.notifyDataSetChanged();
                Log.v(LOG_TAG, "size2 = " + alarms.size());
//                alarms = db.getAllAlarms();
//                adapter = new SwipeRecyclerViewAdapter(alarms, this);
//
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.langRecyclerView);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, adapter);
//                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//                itemTouchHelper.attachToRecyclerView(recyclerView);
                break;
        }
    }
}
