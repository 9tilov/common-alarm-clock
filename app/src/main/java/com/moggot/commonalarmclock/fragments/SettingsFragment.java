package com.moggot.commonalarmclock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenter;
import com.moggot.commonalarmclock.mvp.settings.SettingsView;
import com.moggot.commonalarmclock.presentation.App;
import com.moggot.commonalarmclock.presentation.modules.SettingsScreenModule;

import javax.inject.Inject;

public class SettingsFragment extends Fragment implements SettingsView {

    private static final String ARG_PARAM_ID = "param_id";

    private long id;

    @Inject
    SettingsPresenter presenter;

    public static SettingsFragment newInstance(long id) {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM_ID, id);
        settingsFragment.setArguments(args);
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getAppComponent().plus(new SettingsScreenModule(this)).inject(this);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_PARAM_ID);
            presenter.init(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void setTime(String time) {

    }

    @Override
    public void setDaysCheckbox(boolean isChecked) {

    }

    @Override
    public void setDays(SparseIntArray ids) {

    }

    @Override
    public void showDays() {

    }

    @Override
    public void hideDays() {

    }

    @Override
    public void setIsSnoozeEnable(boolean isSnoozeEnable) {

    }

    @Override
    public void setIsMathEnable(boolean isMathEnable) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setMusicButton(int musicType) {

    }

    @Override
    public void setButtonRadioDrawable(boolean isPlaying) {

    }

    @Override
    public void showToastNoMusicFile() {

    }
}
