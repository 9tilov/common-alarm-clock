package com.moggot.commonalarmclock.mvp.view;

public interface MainFragmentView {

    void deleteAlarm(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyDataSetChanged();
}
