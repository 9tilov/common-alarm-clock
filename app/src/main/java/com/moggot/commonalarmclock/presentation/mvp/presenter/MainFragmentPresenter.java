package com.moggot.commonalarmclock.mvp.presenter;

import com.moggot.commonalarmclock.adapter.ItemTouchHelperAdapter;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.mvp.common.BaseFragmentPresenter;
import com.moggot.commonalarmclock.mvp.view.MainFragmentView;

public interface MainFragmentPresenter extends BaseFragmentPresenter<MainFragmentView>, ItemTouchHelperAdapter {

    void updateList();

    void onItemChangeState(int position, boolean newState);

    Alarm getAlarmFromPosition(int position);

    int getItemsCount();

    long getItemId(int position);
}
