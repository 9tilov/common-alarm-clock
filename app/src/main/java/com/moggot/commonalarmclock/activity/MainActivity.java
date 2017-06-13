package com.moggot.commonalarmclock.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.adapter.SimpleItemTouchHelperCallback;
import com.moggot.commonalarmclock.adapter.SwipeRecyclerViewAdapter;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.mvp.main.MainPresenter;
import com.moggot.commonalarmclock.mvp.main.MainPresenterImpl;
import com.moggot.commonalarmclock.mvp.main.MainView;
import com.moggot.commonalarmclock.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.tutorial.SharedPreference;

public class MainActivity extends AppCompatActivity implements MainView {

    private SwipeRecyclerViewAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();
        Analysis analysis = new Analysis(this);
        analysis.start();

        this.presenter = new MainPresenterImpl(this);
        presenter.initialize();
    }

    private void startOnboarding() {
        if (SharedPreference.LoadTutorialStatus(this)) {
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);
        }
    }

    public void onClickAdd(View view) {
        presenter.onClickAdd(view);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setupViews() {
        this.adapter = new SwipeRecyclerViewAdapter(presenter);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.alarmRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, presenter);
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
}