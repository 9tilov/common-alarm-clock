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
import android.widget.TextView;

import com.moggot.commonalarmclock.MathExample;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.analytics.Analysis;

public class MathFragment extends Fragment {

    private final static String LOG_TAG = MathFragment.class.getSimpleName();

    private ResultListener listener;
    private Analysis analysis;

    public interface ResultListener {
        void checkMathExample(MathExample example);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.analysis = new Analysis(getContext());
        analysis.sendScreenName(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        analysis.sendScreenName(getClass().getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_math, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final MathExample example = createExample();

        TextView etExample = (TextView) view.findViewById(R.id.tvMathExample);
        etExample.setText(example.getNum1() + "+" + example.getNum2());

        final EditText etResult = (EditText) view.findViewById(R.id.etResult);
        etResult.requestFocus();
        if (etResult.requestFocus())
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Button btnResult = (Button) view.findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(etResult);
                if (!etResult.getText().toString().isEmpty())
                    example.setResult(Integer.valueOf(etResult.getText().toString()));
                listener.checkMathExample(example);
            }
        });
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private MathExample createExample() {
        MathExample example = new MathExample();
        example.createExample();
        return example;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
