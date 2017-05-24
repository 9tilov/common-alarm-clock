package com.moggot.commonalarmclock.main;

import android.view.ViewGroup;

import com.moggot.commonalarmclock.AlarmViewHolder;
import com.moggot.commonalarmclock.ItemTouchHelperAdapter;

public interface MainPresenter extends ItemTouchHelperAdapter {

    void updateList();

    void onItemClicked(int position);

    void onItemChangeState(int position, boolean newState);

    AlarmViewHolder createViewHolder(ViewGroup parent, int viewType);

    void bindViewHolder(AlarmViewHolder holder, int position);

    int getItemsCount();

    long getItemId(int position);

}
