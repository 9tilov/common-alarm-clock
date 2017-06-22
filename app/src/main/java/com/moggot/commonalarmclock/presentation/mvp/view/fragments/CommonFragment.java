package com.moggot.commonalarmclock.mvp.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.analytics.Analysis;

public class CommonFragment extends Fragment {

    private String name;
    private Analysis analysis;
    private static final String EXTRA_NAME = "name";

    public CommonFragment() {
    }

    public static CommonFragment newInstance(String name) {
        CommonFragment commonFragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        commonFragment.setArguments(args);
        return commonFragment;
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