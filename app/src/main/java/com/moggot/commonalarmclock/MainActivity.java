package com.moggot.commonalarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private List<Alarm> alarms = new ArrayList<>();
    private AlarmAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvAlarms);
        adapter = new AlarmAdapter(this, R.layout.alarm_item, alarms);
        listView.setAdapter(adapter);

        updateListView();

    }

    public void onClickAdd(View view) {
        Log.v(LOG_TAG, "click");

        Intent intent = new Intent(this, ActivitySettings.class);
        startActivityForResult(intent, 1);
    }

    private void updateListView() {
        DataBase db = new DataBase(this);
        alarms = db.getAllAlarms();
        for (Alarm alarm : alarms) {
            Log.v(LOG_TAG, "day = " + alarm.getDays());
        }
        adapter.update(alarms);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(LOG_TAG, "result");
        updateListView();
    }
}
