package com.moggot.commonalarmclock.mvp.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.moggot.commonalarmclock.ActivitySettings;
import com.moggot.commonalarmclock.AlarmViewHolder;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.Converter;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.mvp.main.MainModel;

public class AdapterPresenterImpl implements AdapterPresenter {

    private Context context;
    private MainModel mainModel;

    public AdapterPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        mainModel.loadData();
    }

    @Override
    public AlarmViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        final AlarmViewHolder viewHolder = new AlarmViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClicked(adapterPosition);
            }
        });

        viewHolder.state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemChangeState(adapterPosition, isChecked);
            }
        });
        return viewHolder;
    }

    @Override
    public void bindViewHolder(AlarmViewHolder viewHolder, int position) {
        Alarm alarm = mainModel.getAlarm(position);
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
        Intent intent = new Intent(context, ActivitySettings.class);
        intent.putExtra(Consts.EXTRA_ID, mainModel.getAlarm(position).getId());
        ((Activity) context).startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
    }

    @Override
    public void onItemChangeState(int position, boolean newState) {
        Alarm alarm = mainModel.getAlarm(position);
        alarm.setState(newState);
        mainModel.editAlarm(alarm, position);
    }
}
