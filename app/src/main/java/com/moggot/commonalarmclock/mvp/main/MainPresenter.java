package com.moggot.commonalarmclock.mvp.main;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.adapter.AlarmViewHolder;
import com.moggot.commonalarmclock.adapter.ItemTouchHelperAdapter;
import com.moggot.commonalarmclock.mvp.BasePresenter;

public interface MainPresenter extends ItemTouchHelperAdapter, BasePresenter {

    void onClickAdd(View view);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onItemClicked(int position);

    void onItemChangeState(int position, boolean newState);

    AlarmViewHolder createViewHolder(ViewGroup parent, int viewType);

    void bindViewHolder(AlarmViewHolder holder, int position);

    int getItemsCount();

    long getItemId(int position);
}
