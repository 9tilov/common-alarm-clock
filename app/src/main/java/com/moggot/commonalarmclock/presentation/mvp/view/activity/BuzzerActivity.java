package com.moggot.commonalarmclock.presentation.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.MathFragment;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.SnoozeFragment;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenter;
import com.moggot.commonalarmclock.presentation.mvp.presenter.BuzzerActivityPresenterImpl;
import com.moggot.commonalarmclock.presentation.mvp.view.BuzzerActivityView;

public class BuzzerActivity extends AppCompatActivity implements BuzzerActivityView
        , SnoozeFragment.SnoozeListener
        , MathFragment.ResultListener {

    private static final String LOG_TAG = BuzzerActivity.class.getSimpleName();

    private BuzzerActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_up_alarm);

        long id = getIntent().getLongExtra(Consts.EXTRA_ID, Consts.NO_ID);
        presenter = new BuzzerActivityPresenterImpl(this);
        presenter.init(id);
    }

    //переопределенный метод для того, чтобы нельзя было нажать кнопку Назад
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        presenter.onDestroy();
    }

    @Override
    public void onClickSnooze() {
        presenter.onClickSnooze();
    }

    @Override
    public void checkResult(int result) {
        presenter.checkResult(result);
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
