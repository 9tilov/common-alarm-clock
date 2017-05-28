package com.moggot.commonalarmclock.mvp.main;

import android.os.Handler;

public class MainPresenterImpl implements MainPresenter {

    private static final String LOG_TAG = MainPresenterImpl.class.getSimpleName();

    private MainView view;
    private MainModel mainModel;

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        mainModel.loadData();
    }

    //Задержка нужна, потому что без нее не работает анимация в onActivityResult
    @Override
    public void onActivityResult() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateList();
            }
        }, 400);
    }

    private void updateList() {
        mainModel.loadData();
        view.notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int adapterPosition, int layoutPosition) {
        mainModel.deleteAlarm(adapterPosition);
        view.deleteAlarm(layoutPosition);
    }

    @Override
    public void onItemShow(int layoutPosition) {
        view.notifyItemRangeChanged(layoutPosition, mainModel.getAlarmsCount());
    }
}