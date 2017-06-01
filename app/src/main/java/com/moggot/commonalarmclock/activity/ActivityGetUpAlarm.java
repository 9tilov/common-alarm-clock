package com.moggot.commonalarmclock.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.MathExample;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.fragments.MathFragment;
import com.moggot.commonalarmclock.fragments.SnoozeFragment;
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

        long id = getIntent().getLongExtra(Consts.EXTRA_ID, Consts.NO_ID);
        presenter = new GetUpPresenterImpl(this);
        presenter.initialize(id);
    }

    //переопределенный метод для того, чтобы нельзя было нажать кнопку Назад
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClickSnooze() {
        presenter.onClickSnooze();
    }

    @Override
    public void checkMathExample(MathExample example) {
        presenter.checkMathExample(example);
    }

    @Override
    public void setupView() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void showToastIncorrectResult() {
        Toast.makeText(this, getString(R.string.incorrect_result), Toast.LENGTH_SHORT).show();
    }
}
