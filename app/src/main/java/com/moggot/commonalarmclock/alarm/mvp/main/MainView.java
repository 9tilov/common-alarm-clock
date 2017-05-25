package com.moggot.commonalarmclock.alarm.mvp.main;

import com.moggot.commonalarmclock.alarm.mvp.BaseView;

public interface MainView extends BaseView {

    void deleteAlarm(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyDataSetChanged();
}
