package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.DataBase;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentMath extends Fragment {

    private final static String LOG_TAG = FragmentMath.class.getSimpleName();

    private TextView tvExample;
    private EditText etResult;

    private Alarm alarm;

    private int firstNum, secondNum;

    public FragmentMath() {
    }

    public static FragmentMath newInstance(long id) {
        FragmentMath fragmentMath = new FragmentMath();
        Bundle args = new Bundle();
        args.putLong(Consts.EXTRA_ID, id);
        fragmentMath.setArguments(args);
        return fragmentMath;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = 0;
        if (getArguments() != null)
            id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity().getApplicationContext());
        alarm = db.getAlarm(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_math, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvExample = (TextView) view.findViewById(R.id.tvMathExample);
        etResult = (EditText) view.findViewById(R.id.etResult);
        etResult.requestFocus();
        if (etResult.requestFocus())
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        createExample();

        Button btnResult = (Button) view.findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isResultCorrect()) {
                    FragmentCreator creator = new FragmentCreator(getActivity());
                    creator.replaceFragment(alarm);

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etResult.getWindowToken(), 0);
                } else
                    incorrectResult();
            }
        });
    }

    private void incorrectResult() {
        Toast.makeText(getActivity(), R.string.incorrect_result, Toast.LENGTH_SHORT).show();
    }

    private void createExample() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        firstNum = list.get(0);
        secondNum = list.get(1);
        String example = String.valueOf(firstNum) + "+" + String.valueOf(secondNum) + "=";
        tvExample.setText(example);
    }

    private boolean isResultCorrect() {
        final String result = etResult.getText().toString();
        if (result.isEmpty())
            return false;
        int sum = firstNum + secondNum;
        return (Integer.valueOf(result) == sum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvExample = null;
        etResult = null;
    }
}
