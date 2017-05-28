package com.moggot.commonalarmclock.mvp.main.adapter;

import android.view.ViewGroup;

import com.moggot.commonalarmclock.AlarmViewHolder;
import com.moggot.commonalarmclock.mvp.main.MainModel;

public interface AdapterPresenter {

    void setMainModel(MainModel mainModel);

    AlarmViewHolder createViewHolder(ViewGroup parent, int viewType);

    void bindViewHolder(AlarmViewHolder holder, int position);

    int getItemsCount();

    long getItemId(int position);

    void onItemClicked(int position);

    void onItemChangeState(int position, boolean newState);
}
