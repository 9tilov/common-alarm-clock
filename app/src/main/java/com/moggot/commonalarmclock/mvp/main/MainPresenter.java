package com.moggot.commonalarmclock.mvp.main;

import com.moggot.commonalarmclock.adapter.ItemTouchHelperAdapter;
import com.moggot.commonalarmclock.alarm.Alarm;

public interface MainPresenter extends ItemTouchHelperAdapter {

    void updateList();

    void onItemChangeState(int position, boolean newState);

    Alarm getAlarmFromPosition(int position);

    int getItemsCount();

    long getItemId(int position);
}
