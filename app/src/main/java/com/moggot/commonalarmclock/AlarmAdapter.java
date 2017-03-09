package com.moggot.commonalarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.Observer.AdapterDisplay;
import com.moggot.commonalarmclock.Observer.AlarmData;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.List;

/**
 * Created by toor on 06.03.17.
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private static class ViewHolder {
        private TextView tvDays;
        private TextView tvTime;
        private TextView tvName;
        private ToggleButton tgState;
        private ImageView ivDelete;
        private ImageView ivMath;
        private ImageView ivSnooze;
        private ImageView ivMusicType;
    }

    private Context context;
    private int layoutResourceID;
    private List<Alarm> alarms;
    private ViewHolder viewHolder;
    private Alarm alarm;

    private final static String LOG_TAG = "AlarmAdapter";

    public AlarmAdapter(Context context, int layoutResourceID, List<Alarm> alarms) {
        super(context, layoutResourceID, alarms);
        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.alarms = alarms;
        viewHolder = new ViewHolder();

    }

    public void update(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceID, parent, false);

        }

        final DataBase db = new DataBase(context);
        db.getAllAlarms();


//        alarm = db.getAlarm(id);

        viewHolder.tvTime = (TextView) view.findViewById(R.id.tvAdapterTime);
        viewHolder.tvName = (TextView) view.findViewById(R.id.tvAdapterName);
        viewHolder.tvDays = (TextView) view.findViewById(R.id.tvAdapterDays);
        viewHolder.ivMusicType = (ImageView) view.findViewById(R.id.ivAdapterMusicType);
        viewHolder.ivSnooze = (ImageView) view.findViewById(R.id.ivAdapterSnooze);
        viewHolder.ivMath = (ImageView) view.findViewById(R.id.ivAdapterMath);
        viewHolder.ivDelete = (ImageView) view.findViewById(R.id.ivAdapterDelete);
        viewHolder.tgState = (ToggleButton) view.findViewById(R.id.tgAdapterState);

        view.setTag(viewHolder);
        viewHolder.tvTime.setTag(alarms.get(position));
        viewHolder.tvName.setTag(alarms.get(position));
        viewHolder.tvDays.setTag(alarms.get(position));
        viewHolder.ivMusicType.setTag(alarms.get(position));
        viewHolder.ivSnooze.setTag(alarms.get(position));
        viewHolder.ivMath.setTag(alarms.get(position));
        viewHolder.ivDelete.setTag(alarms.get(position));
        viewHolder.tgState.setTag(alarms.get(position));

        AlarmData alarmData = new AlarmData();
        AdapterDisplay adapterDisplay = new AdapterDisplay(context, view, alarmData);

        alarmData.setAlarm(alarm);
        adapterDisplay.display();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySettings.class);
                intent.putExtra(Consts.EXTRA_ID, alarm.getId());
                context.startActivity(intent);
            }

        });

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm alarm = getItem(position);
                db.deleteAlarm(alarm);
                alarms.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;

    }

    CompoundButton.OnCheckedChangeListener toggleListiner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

        }
    };


}
