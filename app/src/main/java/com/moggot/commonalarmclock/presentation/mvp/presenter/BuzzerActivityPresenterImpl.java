package com.moggot.commonalarmclock.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.mvp.view.activity.MainActivity;
import com.moggot.commonalarmclock.MathExample;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.mvp.view.fragments.CommonFragment;
import com.moggot.commonalarmclock.mvp.view.fragments.MathFragment;
import com.moggot.commonalarmclock.mvp.view.fragments.SnoozeFragment;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.music.MusicService;
import com.moggot.commonalarmclock.mvp.view.BuzzerActivityView;
import com.moggot.commonalarmclock.mvp.model.BuzzerModel;
import com.moggot.commonalarmclock.mvp.model.BuzzerModelImpl;

import static com.moggot.commonalarmclock.music.Music.MUSIC_TYPE.RADIO;
import static com.moggot.commonalarmclock.music.Music.RADIO_URL;

public class BuzzerActivityPresenterImpl implements BuzzerActivityPresenter {

    private BuzzerModel model;
    private BuzzerActivityView view;

    public BuzzerActivityPresenterImpl(BuzzerActivityView view) {
        this.view = view;
    }

    @Override
    public void init(long id) {
//        this.model = new BuzzerModelImpl(view.getContext());
        model.loadAlarm(id);
        model.startVibro();
        showFragment(model.getIsMathEnable(), model.getIsSnoozeEnable());
        startService();
    }

    private void showFragment(boolean isMathEnable, boolean isSnoozeEnable) {
        if (isMathEnable)
            createMathFragment();
        else {
            if (isSnoozeEnable)
                createSnoozeFragment();
            else
                createCommonFragment();
        }
    }

    private void createMathFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frgmCont, MathFragment.newInstance());
//        ft.commit();
    }

    private void createCommonFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frgmCont, CommonFragment.newInstance(model.getAlarmName()));
//        ft.commit();
    }

    private void createSnoozeFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frgmCont, SnoozeFragment.newInstance(model.getAlarmName()));
//        ft.commit();
    }

    private void startService() {
//        Intent musicIntent = new Intent(view.getContext(), MusicService.class);
//        Music music = new Music(RADIO, RADIO_URL);
//        musicIntent.putExtra(Consts.EXTRA_MUSIC, music);
//        view.getContext().startService(musicIntent);
    }

//    @Override
//    public void onDestroy() {
//        model.stopVibro();
//        stopService();
//        startMainActivity();
//        model.cancelSingleAlarm();
//    }

    @Override
    public void onClickSnooze() {
//        model.snoozeAlarm();
//        ((Activity) view.getContext()).finish();
    }

    @Override
    public void checkMathExample(MathExample example) {
        if (example.isResultCorrect())
            replaceFragment();
        else
            view.showToastIncorrectResult();
    }

    private void replaceFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        if (model.getIsSnoozeEnable())
//            ft.add(R.id.frgmCont, SnoozeFragment.newInstance(model.getAlarmName()));
//        else
//            ft.add(R.id.frgmCont, CommonFragment.newInstance(model.getAlarmName()));
//        ft.commit();
    }

    private void stopService() {
//        Intent intent = new Intent(view.getContext(), MusicService.class);
//        view.getContext().stopService(intent);
    }

    private void startMainActivity() {
//        Intent intent = new Intent(view.getContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//        view.getContext().startActivity(intent);
    }
}
