package com.moggot.commonalarmclock.mvp.main;

import com.moggot.commonalarmclock.mvp.BaseView;

public interface MainView extends BaseView {

    void deleteAlarm(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyDataSetChanged();
}
