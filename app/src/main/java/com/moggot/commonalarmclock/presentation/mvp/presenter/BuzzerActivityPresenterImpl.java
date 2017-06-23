package com.moggot.commonalarmclock.presentation.mvp.presenter;

import com.moggot.commonalarmclock.domain.utils.MathExample;
import com.moggot.commonalarmclock.presentation.mvp.view.BuzzerActivityView;
import com.moggot.commonalarmclock.presentation.mvp.model.BuzzerModel;

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
//        ft.add(R.id.frgmCont, BuzzerMathFragment.newInstance());
//        ft.commit();
    }

    private void createCommonFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frgmCont, BuzzerCommonFragment.newInstance(model.getAlarmName()));
//        ft.commit();
    }

    private void createSnoozeFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frgmCont, BuzzerSnoozeFragment.newInstance(model.getAlarmName()));
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
    public void checkResult(int result) {
        if (MathExample.checkResult(result))
            replaceFragment();
        else
            view.showToastIncorrectResult();
    }

    private void replaceFragment() {
//        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        if (model.getIsSnoozeEnable())
//            ft.add(R.id.frgmCont, BuzzerSnoozeFragment.newInstance(model.getAlarmName()));
//        else
//            ft.add(R.id.frgmCont, BuzzerCommonFragment.newInstance(model.getAlarmName()));
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
