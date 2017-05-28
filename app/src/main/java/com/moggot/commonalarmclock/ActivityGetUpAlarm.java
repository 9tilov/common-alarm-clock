package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.fragments.FragmentCreator;
import com.moggot.commonalarmclock.music.MusicService;

public class ActivityGetUpAlarm extends AppCompatActivity {

    private static final String LOG_TAG = ActivityGetUpAlarm.class.getSimpleName();

    private Alarm alarm;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_up_alarm);

        Analysis analysis = new Analysis(this);
        analysis.start();

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        long id = intent.getLongExtra(Consts.EXTRA_ID, 0);
        if (id == 0)
            return;

        initVibration();

        Intent musicIntent = new Intent(ActivityGetUpAlarm.this, MusicService.class);
//        musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
//        musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
        startService(musicIntent);

        FragmentCreator creator = new FragmentCreator(this);
        creator.createFragment(alarm);
    }

    private void initVibration() {
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] once = {0, 500, 500};
        vibrator.vibrate(once, 0);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        SparseIntArray ids = alarm.getRepeatAlarmIDs();
        if (ids.get(Consts.TOMORROW) != 0) {
            AlarmContext alarmContext = new AlarmContext(alarm, getApplicationContext());
            AlarmManager alarmManager = new AlarmManager();
            alarmManager.cancelAlarm(alarmContext);
        }

        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
