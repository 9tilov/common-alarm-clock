package com.moggot.commonalarmclock.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.adapter.SimpleItemTouchHelperCallback;
import com.moggot.commonalarmclock.adapter.SwipeRecyclerViewAdapter;
import com.moggot.commonalarmclock.mvp.main.MainPresenter;
import com.moggot.commonalarmclock.mvp.main.MainView;
import com.moggot.commonalarmclock.presentation.App;
import com.moggot.commonalarmclock.presentation.modules.MainScreenModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements MainView, View.OnClickListener {

    @Inject
    MainPresenter presenter;
    SwipeRecyclerViewAdapter adapter;

    @BindView(R.id.btnAddAlarm)
    protected Button btnAdd;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getAppComponent().plus(new MainScreenModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupViews(view);
    }

    private void setupViews(View view) {
        ButterKnife.bind(this, view);
        btnAdd.setOnClickListener(this);
        this.adapter = new SwipeRecyclerViewAdapter(presenter);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.alarmRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(getContext(), presenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void deleteAlarm(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickAdd(v);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}