package com.moggot.commonalarmclock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.activity.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.adapter.SimpleItemTouchHelperCallback;
import com.moggot.commonalarmclock.adapter.SwipeRecyclerViewAdapter;
import com.moggot.commonalarmclock.data.SharedPreference;
import com.moggot.commonalarmclock.fragments.MainFragment;
import com.moggot.commonalarmclock.mvp.main.MainPresenter;
import com.moggot.commonalarmclock.mvp.main.MainPresenterImpl;
import com.moggot.commonalarmclock.mvp.main.MainView;
import com.moggot.commonalarmclock.presentation.component.AppComponent;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

    private SwipeRecyclerViewAdapter adapter;
    private MainPresenter presenter;

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();

        createFragment();

        this.presenter = new MainPresenterImpl(this);
        presenter.initialize(0);
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

    private void startOnboarding() {
        if (sharedPreference.loadTutorialStatus()) {
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);
        }
    }


    private void createFragment() {
//        MainFragment mainFragment = getSupportFragmentManager().findFragmentById()
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