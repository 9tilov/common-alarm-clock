package com.moggot.commonalarmclock.mvp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.activity.ActivitySettings;
import com.moggot.commonalarmclock.adapter.AlarmViewHolder;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.Converter;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.animation.CallbackAnimation;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.fragments.SettingsFragment;
import com.moggot.commonalarmclock.music.Music;

import javax.inject.Inject;

import static com.moggot.commonalarmclock.Consts.NO_ID;

public class MainPresenterImpl implements MainPresenter, CallbackAnimation {

    private MainView mainView;
    private MainModel mainModel;

    @Inject
    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        init();
    }

    private void init() {
        this.mainModel = new MainModelImpl(mainView.getContext());
        mainModel.loadData();
    }

    @Override
    public void onClickAdd(View view) {
        AnimationBounce animationBounce = new AnimationBounce(view);
        animationBounce.animate(view.getId(), this);
    }

    //Задержка нужна для работы анимации в onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_SETTINGS:
                Handler handler = new Handler();
                handler.postDelayed(this::updateList, 300);
                break;
        }
    }

    private void updateList() {
        mainModel.loadData();
        mainView.notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int adapterPosition, int layoutPosition) {
        mainModel.deleteAlarm(adapterPosition);
        mainView.deleteAlarm(layoutPosition);
    }

    @Override
    public void onItemShow(int layoutPosition) {
        mainView.notifyItemRangeChanged(layoutPosition, mainModel.getAlarmsCount());
    }

    @Override
    public AlarmViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        final AlarmViewHolder viewHolder = new AlarmViewHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION)
                onItemClicked(adapterPosition);
        });

        viewHolder.state.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION)
                onItemChangeState(adapterPosition, isChecked);
        });
        return viewHolder;
    }

    @Override
    public void bindViewHolder(AlarmViewHolder viewHolder, int position) {
        Alarm alarm = mainModel.getAlarm(position);
        Converter converter = new Converter(mainView.getContext().getResources());
        viewHolder.time.setText(converter.getTimeAsString(alarm.getTimeInMillis()));
        viewHolder.days.setText(converter.getDaysAsString(alarm.getRepeatAlarmIDs()));
        viewHolder.name.setText(alarm.getName());
        viewHolder.state.setChecked(alarm.getState());
        setMathIcon(viewHolder, alarm.getIsMathEnable());
        setSnoozeIcon(viewHolder, alarm.getIsSnoozeEnable());
        setMusicIcon(viewHolder, alarm.getMusicTypeEnum());
    }

    @Override
    public int getItemsCount() {
        return mainModel.getAlarmsCount();
    }

    @Override
    public long getItemId(int position) {
        return mainModel.getAlarm(position).hashCode();
    }

    private void setMusicIcon(AlarmViewHolder viewHolder, Music.MUSIC_TYPE musicType) {
        switch (musicType) {
            case RADIO:
                viewHolder.music.setImageResource(R.drawable.ic_radio);
                break;
            case DEFAULT_RINGTONE:
                viewHolder.music.setImageResource(R.drawable.ic_note);
                break;
            case MUSIC_FILE:
                viewHolder.music.setImageResource(R.drawable.ic_music_file);
                break;
        }
    }

    private void setMathIcon(AlarmViewHolder viewHolder, boolean isMathEnable) {
        if (isMathEnable)
            viewHolder.math.setVisibility(View.VISIBLE);
        else
            viewHolder.math.setVisibility(View.GONE);
    }

    private void setSnoozeIcon(AlarmViewHolder viewHolder, boolean isSnoozeEnable) {
        if (isSnoozeEnable)
            viewHolder.snooze.setVisibility(View.VISIBLE);
        else
            viewHolder.snooze.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(mainView.getContext(), ActivitySettings.class);
        intent.putExtra(Consts.EXTRA_ID, mainModel.getAlarm(position).getId());
        ((Activity) mainView.getContext()).startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
    }

    @Override
    public void onItemChangeState(int position, boolean newState) {
        Alarm alarm = mainModel.getAlarm(position);
        alarm.setState(newState);
        mainModel.editAlarm(alarm, position);
    }

    @Override
    public void endAnimationAction(int actionID) {
        SettingsFragment settingsFragment = SettingsFragment.newInstance(NO_ID);
        ActivityUtils.addFragmentToActivity(((FragmentActivity) mainView.getContext()).getSupportFragmentManager(), settingsFragment, R.id.root_frame);
    }

    @Override
    public void onDestroy() {

    }
}