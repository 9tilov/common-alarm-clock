package com.moggot.commonalarmclock;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.fragments.FragmentCommon;
import com.moggot.commonalarmclock.fragments.FragmentMath;
import com.moggot.commonalarmclock.fragments.FragmentSnooze;
import com.moggot.commonalarmclock.music.MusicService;

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

        final Vibrator vibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] once = {0, 500, 500};
        vibration.vibrate(once, 0);
        Intent musicIntent = new Intent(ActivityAlarm.this, MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
        musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
        startService(musicIntent);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putLong(Consts.EXTRA_ID, id);
        fragmentMath.setArguments(bundle);

        if (alarm.getIsMathEnable())
            ft.add(R.id.frgmCont, fragmentMath);
        else {
            if (alarm.getIsSnoozeEnable())
                ft.add(R.id.frgmCont, fragmentSnooze);
            else
                ft.add(R.id.frgmCont, fragmentCommon);
        }

        ft.commit();
    }

}
