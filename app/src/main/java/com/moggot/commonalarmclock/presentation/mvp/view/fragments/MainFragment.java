package com.moggot.commonalarmclock.presentation.mvp.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.presentation.adapter.SimpleItemTouchHelperCallback;
import com.moggot.commonalarmclock.presentation.adapter.SwipeRecyclerViewAdapter;
import com.moggot.commonalarmclock.presentation.animation.AnimationAddButton;
import com.moggot.commonalarmclock.presentation.animation.AnimationBounce;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.presentation.di.App;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.di.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.mvp.presenter.MainFragmentPresenter;
import com.moggot.commonalarmclock.presentation.mvp.view.MainFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements MainFragmentView {

    @Inject
    MainFragmentPresenter presenter;

    private SwipeRecyclerViewAdapter adapter;

    @BindView(R.id.btnAddAlarm)
    Button btnAdd;

    @BindView(R.id.alarmRecyclerView)
    RecyclerView recyclerView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        App.getInstance().getAppComponent().plus(new AlarmModule()).plus(new MainScreenModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.bindView(this);
        setupViews(view);
        presenter.updateList();
    }

    private void setupViews(View view) {
        ButterKnife.bind(this, view);
        btnAdd.setOnClickListener(this::addAlarm);
        this.adapter = new SwipeRecyclerViewAdapter(getResources(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(getContext(), presenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void addAlarm(View view) {
        AnimationBounce animationBounce = new AnimationAddButton(getContext());
        animationBounce.animate(view);
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
    public void createFragment(long id) {
        SettingsFragment settingsFragment = SettingsFragment.newInstance(id);
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        ActivityUtils.replaceFragmentInActivity(fragmentManager, settingsFragment, R.id.root_frame);
    }
}