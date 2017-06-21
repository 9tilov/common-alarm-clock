package com.moggot.commonalarmclock.mvp.main;

import android.os.Handler;

import com.moggot.commonalarmclock.alarm.Alarm;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private MainModel mainModel;

    @Inject
    public MainPresenterImpl(MainView mainView, MainModelImpl mainModel) {
        this.mainView = mainView;
        this.mainModel = mainModel;
        init();
    }

    private void init() {
        mainModel.loadData();
    }

    //Задержка нужна для работы анимации в onActivityResult

    public void updateList() {
        Handler handler = new Handler();
        handler.postDelayed(this::update, 300);

    }

    private void update() {
        mainModel.loadData();
        mainView.notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int adapterPosition, int layoutPosition) {
        mainModel.deleteAlarm(adapterPosition);
        mainView.deleteAlarm(layoutPosition);
    }

    @Override
    public void onItemShow(int layoutPosition) {
        mainView.notifyItemRangeChanged(layoutPosition, mainModel.getAlarmsCount());
    }

    @Override
    public int getItemsCount() {
        return mainModel.getAlarmsCount();
    }

    @Override
    public long getItemId(int position) {
        return mainModel.getAlarm(position).hashCode();
    }

    @Override
    public void onItemChangeState(int position, boolean newState) {
        Alarm alarm = mainModel.getAlarm(position);
        alarm.setState(newState);
        mainModel.editAlarm(alarm, position);
    }

    @Override
    public Alarm getAlarmFromPosition(int position) {
        return mainModel.getAlarm(position);
    }
}