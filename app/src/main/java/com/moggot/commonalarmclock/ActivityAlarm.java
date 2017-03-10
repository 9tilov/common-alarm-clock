package com.moggot.commonalarmclock;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.fragments.FragmentCommon;
import com.moggot.commonalarmclock.fragments.FragmentMath;
import com.moggot.commonalarmclock.fragments.FragmentSnooze;

public class ActivityAlarm extends AppCompatActivity {

    private static final String LOG_TAG = "ActivityAlarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        long id = intent.getLongExtra(Consts.EXTRA_ID, 0);
        if (id == 0)
            return;

        DataBase db = new DataBase(this);
        Alarm alarm = db.getAlarm(id);

        FragmentCommon fragmentCommon = new FragmentCommon();
        FragmentMath fragmentMath = new FragmentMath();
        FragmentSnooze fragmentSnooze = new FragmentSnooze();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, fragmentCommon);

        if (alarm.getIsMathEnable())
            ft.add(R.id.frgmCont, fragmentMath);
        else {
            if (alarm.getIsSnoozeEnable())
                ft.add(R.id.frgmCont, fragmentSnooze);
            else
                ft.add(R.id.frgmCont, fragmentCommon);
        }

        ft.commit();

//        Log.v(LOG_TAG, "name = " + alarm.getName());

    }
}
