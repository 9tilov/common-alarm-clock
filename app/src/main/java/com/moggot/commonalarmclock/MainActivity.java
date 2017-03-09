package com.moggot.commonalarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private List<Alarm> alarms = new ArrayList<>();
    private AlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.lvAlarms);
        adapter = new AlarmAdapter(this, R.layout.alarm_item, alarms);
        listView.setAdapter(adapter);

        updateListView();

    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
    }

    private void updateListView() {
        DataBase db = new DataBase(this);
        alarms = db.getAllAlarms();
        adapter.update(alarms);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Consts.REQUEST_CODE_ACTIVITY_SETTINGS:
                    updateListView();
                    break;
            }
        }
    }
}
