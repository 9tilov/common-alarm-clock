package com.moggot.commonalarmclock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.mvp.main.adapter.AdapterPresenter;
import com.moggot.commonalarmclock.mvp.main.adapter.AdapterPresenterImpl;
import com.moggot.commonalarmclock.mvp.main.MainModelImpl;

public class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private static final String LOG_TAG = SwipeRecyclerViewAdapter.class.getSimpleName();

    private AdapterPresenter presenter;

    private Context context;

    public SwipeRecyclerViewAdapter(Context context) {
        this.context = context;

        setupMVP();
        setHasStableIds(true);
    }

    private void setupMVP() {
        AdapterPresenterImpl presenter = new AdapterPresenterImpl(context);
        MainModelImpl model = new MainModelImpl(context.getApplicationContext());
        presenter.setMainModel(model);
        this.presenter = presenter;
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
