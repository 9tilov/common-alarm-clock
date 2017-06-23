package com.moggot.commonalarmclock.presentation.mvp.presenter;

import com.moggot.commonalarmclock.presentation.adapter.ItemTouchHelperAdapter;
import com.moggot.commonalarmclock.data.alarm.Alarm;
import com.moggot.commonalarmclock.presentation.mvp.common.BaseFragmentPresenter;
import com.moggot.commonalarmclock.presentation.mvp.view.MainFragmentView;

public interface MainFragmentPresenter extends BaseFragmentPresenter<MainFragmentView>, ItemTouchHelperAdapter {

    void updateList();

    void onItemChangeState(int position, boolean newState);

    Alarm getAlarmFromPosition(int position);

    int getItemsCount();

    long getItemId(int position);

    void openSettings(long id);
}
