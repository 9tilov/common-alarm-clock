package com.moggot.commonalarmclock.presentation.mvp.view;

public interface MainFragmentView {

    void deleteAlarm(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyDataSetChanged();
}
