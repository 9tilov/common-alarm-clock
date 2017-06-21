package com.moggot.commonalarmclock.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.Converter;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.fragments.SettingsFragment;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.mvp.main.MainPresenter;

public class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private MainPresenter presenter;
    private Context context;

    public SwipeRecyclerViewAdapter(Context context, MainPresenter presenter) {
        this.presenter = presenter;
        this.context = context;

        setHasStableIds(true);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_item, viewGroup, false);
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

    private void onItemChangeState(int position, boolean newState) {
        presenter.onItemChangeState(position, newState);
    }

    private void onItemClicked(int position) {
        long id = presenter.getAlarmFromPosition(position).getId();
        createSettingsFragment(id);
    }

    private void createSettingsFragment(long id) {
        SettingsFragment settingsFragment = SettingsFragment.newInstance(id);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        ActivityUtils.replaceFragmentInActivity(fragmentManager, settingsFragment, R.id.root_frame);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder viewHolder, int position) {
        Alarm alarm = presenter.getAlarmFromPosition(position);
        Converter converter = new Converter(context.getResources());
        viewHolder.time.setText(converter.getTimeAsString(alarm.getTimeInMillis()));
        viewHolder.days.setText(converter.getDaysAsString(alarm.getRepeatAlarmIDs()));
        viewHolder.name.setText(alarm.getName());
        viewHolder.state.setChecked(alarm.getState());
        setMathIcon(viewHolder, alarm.getIsMathEnable());
        setSnoozeIcon(viewHolder, alarm.getIsSnoozeEnable());
        setMusicIcon(viewHolder, alarm.getMusicTypeEnum());
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }

    @Override
    public long getItemId(int position) {
        return presenter.getItemId(position);
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

    private void setMusicIcon(AlarmViewHolder viewHolder, Music.MUSIC_TYPE musicType) {
        switch (musicType) {
            case RADIO:
                viewHolder.music.setImageResource(R.drawable.ic_radio);
                break;
            case DEFAULT_RINGTONE:
                viewHolder.music.setImageResource(R.drawable.ic_ringtone);
                break;
            case MUSIC_FILE:
                viewHolder.music.setImageResource(R.drawable.ic_music_file);
                break;
        }
    }
}
