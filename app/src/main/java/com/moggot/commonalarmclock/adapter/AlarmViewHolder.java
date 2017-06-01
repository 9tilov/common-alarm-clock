package com.moggot.commonalarmclock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.R;

public class AlarmViewHolder extends RecyclerView.ViewHolder {

    public TextView time;
    public TextView days;
    public TextView name;
    public ToggleButton state;
    public ImageView math;
    public ImageView snooze;
    public ImageView music;

    public AlarmViewHolder(final View itemView) {
        super(itemView);
        time = (TextView) itemView.findViewById(R.id.tvAdapterTime);
        days = (TextView) itemView.findViewById(R.id.tvAdapterDays);
        name = (TextView) itemView.findViewById(R.id.tvAdapterName);
        state = (ToggleButton) itemView.findViewById(R.id.tgAdapterState);
        math = (ImageView) itemView.findViewById(R.id.ivAdapterMath);
        snooze = (ImageView) itemView.findViewById(R.id.ivAdapterSnooze);
        music = (ImageView) itemView.findViewById(R.id.ivAdapterMusicType);
    }
}