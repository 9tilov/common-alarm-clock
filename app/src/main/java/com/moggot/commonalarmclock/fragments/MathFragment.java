package com.moggot.commonalarmclock.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.moggot.commonalarmclock.R;

public class MathFragment extends Fragment {

    private final static String LOG_TAG = MathFragment.class.getSimpleName();

    private ResultListener listener;

    public interface ResultListener {
        void setMathResult(int result);
    }

    public MathFragment() {
    }

    public static MathFragment newInstance() {
        return new MathFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.listener = (ResultListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ResultListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_math, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText etResult = (EditText) view.findViewById(R.id.etResult);
        etResult.requestFocus();
        if (etResult.requestFocus())
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Button btnResult = (Button) view.findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etResult.getWindowToken(), 0);
                listener.setMathResult(Integer.valueOf(etResult.getText().toString()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
