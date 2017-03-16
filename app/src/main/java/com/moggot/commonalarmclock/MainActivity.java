package com.moggot.commonalarmclock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.animation.AddAlarmAnimationBounce;
import com.moggot.commonalarmclock.animation.AnimationBounce;

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
        adapter = new AlarmAdapter(this, alarms);
        listView.setAdapter(adapter);

        updateListView();

    }

    public void onClickAdd(View view) {
        AnimationBounce animation = new AddAlarmAnimationBounce(this);
        animation.animate(view);
    }

    private void updateListView() {
        DataBase db = new DataBase(this);
        alarms = db.getAllAlarms();
        adapter.update(alarms);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_SETTINGS:
                updateListView();
                break;
        }
    }
}
