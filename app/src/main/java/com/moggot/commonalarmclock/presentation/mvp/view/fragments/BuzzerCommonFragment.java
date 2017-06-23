package com.moggot.commonalarmclock.presentation.mvp.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.domain.analytics.Analysis;

public class BuzzerCommonFragment extends Fragment {

    private String name;
    private Analysis analysis;
    private static final String EXTRA_NAME = "name";

    public BuzzerCommonFragment() {
    }

    public static BuzzerCommonFragment newInstance(String name) {
        BuzzerCommonFragment buzzerCommonFragment = new BuzzerCommonFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        buzzerCommonFragment.setArguments(args);
        return buzzerCommonFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.analysis = new Analysis(getContext());
        analysis.sendScreenName(this.getClass().getSimpleName());

        if (getArguments() != null)
            this.name = getArguments().getString(EXTRA_NAME);
    }

    @Override
    public void onResume() {
        super.onResume();
        analysis.sendScreenName(getClass().getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_common, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.tvName)).setText(name);

        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}