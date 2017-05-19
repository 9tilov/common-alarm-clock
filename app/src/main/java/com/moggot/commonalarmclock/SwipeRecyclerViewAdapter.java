package com.moggot.commonalarmclock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.List;

public class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<SwipeRecyclerViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private static final String LOG_TAG = SwipeRecyclerViewAdapter.class.getSimpleName();

    private List<Alarm> alarms;
    private static OnItemTouchListener onItemTouchListener;
    private Converter converter;
    private DataBase db;

    public SwipeRecyclerViewAdapter(Context context, List<Alarm> alarms, OnItemTouchListener listener) {
        this.alarms = alarms;
        this.db = new DataBase(context);
        onItemTouchListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.converter = new Converter(viewGroup.getContext().getResources());

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Alarm alarm = alarms.get(position);
        viewHolder.time.setText(converter.getTimeAsString(alarm.getTimeInMillis()));
        viewHolder.days.setText(converter.getDaysAsString(alarm.getRepeatAlarmIDs()));
        viewHolder.name.setText(alarm.getName());
        viewHolder.state.setChecked(alarm.getState());
        setMathIcon(viewHolder, alarm.getIsMathEnable());
        setSnoozeIcon(viewHolder, alarm.getIsSnoozeEnable());
        setMusicIcon(viewHolder, alarm.getMusicTypeEnum());
    }

    @Override
    public void onItemDismiss(int position) {
        db.removeAlarm(alarms.get(position));
        alarms.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemShow(int position) {
        notifyItemRangeChanged(position, alarms.size());
    }

    public Alarm getAlarmAtPosition(int position) {
        return alarms.get(position);
    }

    @Override
    public int getItemCount() {
        return alarms == null ? 0 : alarms.size();
    }

    private void setMusicIcon(ViewHolder viewHolder, Consts.MUSIC_TYPE musicType) {
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

    private void setMathIcon(ViewHolder viewHolder, boolean isMathEnable) {
        if (isMathEnable)
            viewHolder.math.setVisibility(View.VISIBLE);
        else
            viewHolder.math.setVisibility(View.GONE);
    }

    private void setSnoozeIcon(ViewHolder viewHolder, boolean isSnoozeEnable) {
        if (isSnoozeEnable)
            viewHolder.snooze.setVisibility(View.VISIBLE);
        else
            viewHolder.snooze.setVisibility(View.GONE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView days;
        private TextView name;
        private ToggleButton state;
        private ImageView math;
        private ImageView snooze;
        private ImageView music;

        public ViewHolder(final View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tvAdapterTime);
            days = (TextView) itemView.findViewById(R.id.tvAdapterDays);
            name = (TextView) itemView.findViewById(R.id.tvAdapterName);
            state = (ToggleButton) itemView.findViewById(R.id.tgAdapterState);
            math = (ImageView) itemView.findViewById(R.id.ivAdapterMath);
            snooze = (ImageView) itemView.findViewById(R.id.ivAdapterSnooze);
            music = (ImageView) itemView.findViewById(R.id.ivAdapterMusicType);

            state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onStateChange(v, getLayoutPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                }
            });
        }
    }
}
