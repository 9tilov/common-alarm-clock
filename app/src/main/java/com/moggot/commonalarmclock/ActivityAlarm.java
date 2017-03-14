package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.fragments.FragmentCreator;
import com.moggot.commonalarmclock.music.MusicService;

public class ActivityAlarm extends AppCompatActivity {

    private static final String LOG_TAG = "ActivityAlarm";

    private Vibrator vibrator;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Log.v(LOG_TAG, "start");

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        long id = intent.getLongExtra(Consts.EXTRA_ID, 0);
        if (id == 0)
            return;

        DataBase db = new DataBase(this);
        alarm = db.getAlarm(id);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] once = {0, 500, 500};
        vibrator.vibrate(once, 0);

        Intent musicIntent = new Intent(ActivityAlarm.this, MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
        musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
        startService(musicIntent);

        FragmentCreator creator = new FragmentCreator(this);
        creator.createFragment(alarm);
    }

    @Override
    public void onBackPressed() {
    }

    public void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        SparseIntArray ids = alarm.getIDs();
        if (ids.get(Consts.DAYS.TOMORROW.getCode()) != 0) {
            AlarmContext alarmContext = new AlarmContext(alarm, this);
            AlarmManager alarmManager = new AlarmManager();
            alarmManager.cancelAlarm(alarmContext);
        }
    }
}
