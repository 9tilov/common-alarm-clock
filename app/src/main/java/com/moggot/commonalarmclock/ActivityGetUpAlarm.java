package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.fragments.FragmentCreator;
import com.moggot.commonalarmclock.music.MusicService;

public class ActivityGetUpAlarm extends AppCompatActivity {

    private static final String LOG_TAG = "ActivityGetUpAlarm";

    private Vibrator vibrator;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_up_alarm);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        long id = intent.getLongExtra(Consts.EXTRA_ID, 0);
        if (id == 0)
            return;

        DataBase db = new DataBase(this);
        this.alarm = db.getAlarm(id);

        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] once = {0, 500, 500};
        vibrator.vibrate(once, 0);

        Intent musicIntent = new Intent(ActivityGetUpAlarm.this, MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
        musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
        startService(musicIntent);

        FragmentCreator creator = new FragmentCreator(this);
        creator.createFragment(alarm);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        SparseIntArray ids = alarm.getIDs();
        if (ids.get(Consts.TOMORROW) != 0) {
            AlarmContext alarmContext = new AlarmContext(alarm, this);
            AlarmManager alarmManager = new AlarmManager();
            alarmManager.cancelAlarm(alarmContext);
        }

        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
