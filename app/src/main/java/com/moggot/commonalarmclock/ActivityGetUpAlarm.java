package com.moggot.commonalarmclock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.fragments.MathFragment;
import com.moggot.commonalarmclock.fragments.SnoozeFragment;
import com.moggot.commonalarmclock.mvp.getUpScreen.GetUpModelImpl;
import com.moggot.commonalarmclock.mvp.getUpScreen.GetUpPresenter;
import com.moggot.commonalarmclock.mvp.getUpScreen.GetUpPresenterImpl;
import com.moggot.commonalarmclock.mvp.getUpScreen.GetUpView;

public class ActivityGetUpAlarm extends AppCompatActivity implements GetUpView
        , SnoozeFragment.SnoozeListener
        , MathFragment.ResultListener {

    private static final String LOG_TAG = ActivityGetUpAlarm.class.getSimpleName();

    private GetUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_up_alarm);

        Analysis analysis = new Analysis(this);
        analysis.start();

        setWindowProperties();
        setupMVP();

        long id = getIntent().getLongExtra(Consts.EXTRA_ID, 0);
        presenter.startAlarmRing(id);
    }

    void setupMVP() {
        GetUpPresenterImpl presenter = new GetUpPresenterImpl(this);
        GetUpModelImpl model = new GetUpModelImpl(this);
        presenter.setModel(model);
        this.presenter = presenter;
    }

    private void setWindowProperties() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopAlarmRing();
//        SparseIntArray ids = alarm.getRepeatAlarmIDs();
//        if (ids.get(Consts.TOMORROW) != 0) {
//            AlarmContext alarmContext = new AlarmContext(alarm, getApplicationContext());
//            AlarmManager alarmManager = new AlarmManager();
//            alarmManager.cancelAlarm(alarmContext);
//        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClickSnooze() {
        presenter.snooze();
    }

    @Override
    public void setMathResult(int result) {
        MathExample mathExample = new MathExample();
        if (mathExample.checkResult(result))
            presenter.replaceFragment();
    }
}
