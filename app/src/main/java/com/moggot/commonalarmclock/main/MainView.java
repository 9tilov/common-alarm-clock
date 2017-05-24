package com.moggot.commonalarmclock.main;

public interface MainView extends BaseView {

    void deleteAlarm(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyDataSetChanged();
}
