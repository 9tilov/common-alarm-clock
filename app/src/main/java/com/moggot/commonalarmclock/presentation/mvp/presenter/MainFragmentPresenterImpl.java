package com.moggot.commonalarmclock.presentation.mvp.presenter;

import android.os.Handler;

import com.moggot.commonalarmclock.data.DataBase;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.presentation.di.App;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.mvp.model.MainModel;
import com.moggot.commonalarmclock.presentation.mvp.model.MainModelImpl;
import com.moggot.commonalarmclock.presentation.mvp.view.MainFragmentView;
import com.moggot.commonalarmclock.schedule.AlarmScheduler;

import javax.inject.Inject;

public class MainFragmentPresenterImpl implements MainFragmentPresenter {

    private MainFragmentView view;
    private MainModel model;

    @Inject
    DataBase dataBase;
    @Inject
    AlarmScheduler alarmScheduler;

    @Inject
    public MainFragmentPresenterImpl() {
        App.getInstance().getAppComponent().plus(new AlarmModule()).inject(this);
        setModel();
    }

    @Override
    public void setModel() {
        this.model = new MainModelImpl(dataBase, alarmScheduler);
        model.loadData();
    }

    @Override
    public void bindView(MainFragmentView view) {
        this.view = view;
    }

    //Задержка нужна для работы анимации в onActivityResult
    public void updateList() {
        Handler handler = new Handler();
        handler.postDelayed(this::update, 300);

    }

    private void update() {
        model.loadData();
        view.notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int adapterPosition, int layoutPosition) {
        model.deleteAlarm(adapterPosition);
        view.deleteAlarm(layoutPosition);
    }

    @Override
    public void onItemShow(int layoutPosition) {
        view.notifyItemRangeChanged(layoutPosition, model.getAlarmsCount());
    }

    @Override
    public int getItemsCount() {
        return model.getAlarmsCount();
    }

    @Override
    public long getItemId(int position) {
        return model.getAlarm(position).hashCode();
    }

    @Override
    public void onItemChangeState(int position, boolean newState) {
        Alarm alarm = model.getAlarm(position);
        alarm.setState(newState);
        model.editAlarm(alarm, position);
    }

    @Override
    public Alarm getAlarmFromPosition(int position) {
        return model.getAlarm(position);
    }


}