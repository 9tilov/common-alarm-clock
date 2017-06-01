package com.moggot.commonalarmclock;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.mvp.main.MainPresenter;

public class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private static final String LOG_TAG = SwipeRecyclerViewAdapter.class.getSimpleName();

    private MainPresenter presenter;

    public SwipeRecyclerViewAdapter(MainPresenter presenter) {
        this.presenter = presenter;



        //hello
        setHasStableIds(true);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return presenter.createViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder viewHolder, int position) {
        presenter.bindViewHolder(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }

    @Override
    public long getItemId(int position) {
        return presenter.getItemId(position);
    }

}
