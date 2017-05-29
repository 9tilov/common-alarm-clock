package com.moggot.commonalarmclock.mvp.getUpScreen;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.MainActivity;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.fragments.CommonFragment;
import com.moggot.commonalarmclock.fragments.MathFragment;
import com.moggot.commonalarmclock.fragments.SnoozeFragment;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.music.MusicService;

public class GetUpPresenterImpl implements GetUpPresenter {

    private GetUpModel model;
    private GetUpView view;

    public GetUpPresenterImpl(GetUpView view) {
        this.view = view;
    }

    @Override
    public void setModel(GetUpModel model) {
        this.model = model;
    }

    @Override
    public void startAlarmRing(long id) {
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
        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, MathFragment.newInstance());
        ft.commit();
    }

    private void createCommonFragment() {
        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, CommonFragment.newInstance(model.getAlarmName()));
        ft.commit();
    }

    private void createSnoozeFragment() {
        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, SnoozeFragment.newInstance(model.getAlarmName()));
        ft.commit();
    }

    private void startService() {
        Intent musicIntent = new Intent(view.getContext(), MusicService.class);
        Music music = model.getMusic();
        musicIntent.putExtra(Consts.EXTRA_MUSIC, music);
        view.getContext().startService(musicIntent);
    }

    @Override
    public void stopAlarmRing() {
        model.stopVibro();
        stopService();
        startMainActivity();
    }

    @Override
    public void snooze() {
        ((Activity) view.getContext()).finish();
    }

    private void stopService() {
        Intent intent = new Intent(view.getContext(), MusicService.class);
        view.getContext().stopService(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(intent);
    }

    @Override
    public void replaceFragment() {
        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
        if (model.getIsSnoozeEnable())
            ft.add(R.id.frgmCont, SnoozeFragment.newInstance(model.getAlarmName()));
        else
            ft.add(R.id.frgmCont, CommonFragment.newInstance(model.getAlarmName()));
        ft.commit();
    }
}
