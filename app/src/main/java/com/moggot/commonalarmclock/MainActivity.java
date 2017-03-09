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
    Alarm alarm1;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBase(this);

        listView = (ListView) findViewById(R.id.lvAlarms);
        adapter = new AlarmAdapter(this, R.layout.alarm_item, alarms);
        listView.setAdapter(adapter);
    }

    public void onClickAdd(View view) {
        Log.v(LOG_TAG, "click");

        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);

    }
}
