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
import com.moggot.commonalarmclock.mvp.buzzer.BuzzerPresenter;
import com.moggot.commonalarmclock.mvp.buzzer.BuzzerPresenterImpl;
import com.moggot.commonalarmclock.mvp.buzzer.BuzzerView;

public class ActivityBuzzer extends AppCompatActivity implements BuzzerView
        , SnoozeFragment.SnoozeListener
        , MathFragment.ResultListener {

    private static final String LOG_TAG = ActivityBuzzer.class.getSimpleName();

    private BuzzerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_up_alarm);

        long id = getIntent().getLongExtra(Consts.EXTRA_ID, Consts.NO_ID);
        presenter = new BuzzerPresenterImpl(this);
        presenter.init(id);
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

    private void setupViews() {
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
