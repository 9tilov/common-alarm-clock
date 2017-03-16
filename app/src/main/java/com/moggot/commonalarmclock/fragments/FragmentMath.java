package com.moggot.commonalarmclock.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by toor on 10.03.17.
 */

public class FragmentMath extends Fragment {

    private final static String LOG_TAG = "FragmentMath";

    private TextView tvExample;
    private EditText etResult;

    private int firstNum, secondNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_math, container, false);

        tvExample = (TextView) view.findViewById(R.id.tvMathExample);
        etResult = (EditText) view.findViewById(R.id.etResult);
        etResult.requestFocus();
        if (etResult.requestFocus())
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        formExample();

        final long id = getArguments().getLong(Consts.EXTRA_ID);
        DataBase db = new DataBase(getActivity());
        final Alarm alarm = db.getAlarm(id);

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

        return view;
    }

    private void incorrectResult() {
        Toast.makeText(getActivity(), R.string.incorrect_result, Toast.LENGTH_SHORT).show();
    }

    private void formExample() {
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
}
