package com.moggot.commonalarmclock.presentation.mvp.view.fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.debug.hv.ViewServer;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.domain.utils.Log;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.presentation.animation.AnimationBounce;
import com.moggot.commonalarmclock.presentation.animation.AnimationMusicRadioButton;
import com.moggot.commonalarmclock.presentation.animation.AnimationSaveButton;
import com.moggot.commonalarmclock.domain.utils.NetworkConnectionChecker;
import com.moggot.commonalarmclock.domain.music.Music;
import com.moggot.commonalarmclock.presentation.di.App;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;
import com.moggot.commonalarmclock.presentation.di.modules.MainScreenModule;
import com.moggot.commonalarmclock.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.moggot.commonalarmclock.presentation.mvp.view.SettingsFragmentView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.moggot.commonalarmclock.domain.music.Music.MUSIC_TYPE.RADIO;
import static com.moggot.commonalarmclock.domain.music.Music.RADIO_URL;

public class SettingsFragment extends Fragment implements
        SettingsFragmentView, TextWatcher, View.OnClickListener {

    private static final String ARG_PARAM_ID = "param_id";

    private SparseIntArray tbDaysOfWeek;

    @BindView(R.id.rgMusicType)
    RadioGroup musicRadioGroup;

    @BindView(R.id.etAlarmName)
    EditText etName;

    @BindView(R.id.tvAlarmTime)
    TextView tvTime;

    @BindView(R.id.checkBoxSnooze)
    CheckBox checkBoxSnooze;

    @BindView(R.id.checkBoxMath)
    CheckBox checkBoxMath;

    @BindView(R.id.checkBoxRepeat)
    CheckBox checkBoxRepeate;

    @BindView(R.id.rlDays)
    RelativeLayout rlDays;

    @BindView(R.id.btnMusic)
    Button btnMusic;

    @BindView(R.id.btnSaveAlarm)
    Button btnSave;

    @Inject
    SettingsFragmentPresenter presenter;

    @Inject
    NetworkConnectionChecker connectionChecker;

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
        setRetainInstance(true);
        App.getInstance().getAppComponent().plus(new AlarmModule()).plus(new MainScreenModule()).inject(this);

        if (getArguments() != null) {
            long id = getArguments().getLong(ARG_PARAM_ID);
            presenter.loadAlarmAndCreatePlayer(id);
        }

        ViewServer.get(getContext()).addWindow(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.bindView(this);
        setListeners(view);
        presenter.setupViews();
    }

    private void setListeners(View view) {
        ButterKnife.bind(this, view);
        musicRadioGroup.setOnCheckedChangeListener((group, checkedId) -> onCheckedChangedRadioGroup(group));
        etName.addTextChangedListener(this);
        tvTime.setOnClickListener(v -> showTimerPickerDialog());
        checkBoxSnooze.setOnCheckedChangeListener((buttonView, isChecked) -> setCheckedSnooze(isChecked));
        checkBoxMath.setOnCheckedChangeListener((buttonView, isChecked) -> setCheckedMath(isChecked));
        checkBoxRepeate.setOnCheckedChangeListener((buttonView, isChecked) -> setCheckedRepeate(isChecked));
        btnMusic.setOnClickListener(this::clickMusicButton);
        btnSave.setOnClickListener(this::saveAlarm);
        setListenersToDayButtons(view);
    }

    private void onCheckedChangedRadioGroup(RadioGroup radioGroup) {
        Music.MUSIC_TYPE type = radioGroupIndexToMusicType(radioGroup);
        switch (type) {
            case MUSIC_FILE:
                presenter.stopPlaying();
                break;
            case RADIO:
                Music music = new Music(RADIO, RADIO_URL);
                presenter.setMusic(music);
                break;
            case RINGTONE:
                presenter.stopPlaying();
                break;
            default:
                break;
        }

        setMusicButtonDrawable(type);
    }

    private Music.MUSIC_TYPE radioGroupIndexToMusicType(RadioGroup radioGroup) throws NullPointerException {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = ButterKnife.findById(getView(), radioButtonId);
        int index = radioGroup.indexOfChild(radioButton);
        return Music.MUSIC_TYPE.fromInteger(index);
    }

    private void showTimerPickerDialog() {
        TimePickerDialog timePicker = new TimePickerDialog(getContext()
                , (view, hourOfDay, minute) -> presenter.setSelectedTime(hourOfDay, minute)
                , presenter.getHour()
                , presenter.getMinute()
                , true);
        timePicker.show();
    }

    private void setCheckedSnooze(boolean isChecked) {
        presenter.setCheckedSnooze(isChecked);
    }

    private void setCheckedMath(boolean ischecked) {
        presenter.setCheckedMath(ischecked);
    }

    private void setCheckedRepeate(boolean isChecked) {
        presenter.setCheckedRepeate(isChecked);
    }

    private void clickMusicButton(View view) {
        AnimationBounce animationBounce = new AnimationMusicRadioButton(getContext());
        animationBounce.animate(view);

        presenter.clickPlay();
    }

    private void saveAlarm(View view) {
        presenter.saveAlarm();

        AnimationBounce animationBounce = new AnimationSaveButton(getContext());
        animationBounce.animate(view);
    }

    private void setListenersToDayButtons(View view) {
        this.tbDaysOfWeek = new SparseIntArray();
        tbDaysOfWeek.put(R.id.tbMonday, Calendar.MONDAY);
        tbDaysOfWeek.put(R.id.tbTuesday, Calendar.TUESDAY);
        tbDaysOfWeek.put(R.id.tbWednesday, Calendar.WEDNESDAY);
        tbDaysOfWeek.put(R.id.tbThursday, Calendar.THURSDAY);
        tbDaysOfWeek.put(R.id.tbFriday, Calendar.FRIDAY);
        tbDaysOfWeek.put(R.id.tbSaturday, Calendar.SATURDAY);
        tbDaysOfWeek.put(R.id.tbSunday, Calendar.SUNDAY);
        for (int i = 0; i < tbDaysOfWeek.size(); ++i)
            ButterKnife.findById(view, tbDaysOfWeek.keyAt(i)).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setRadioButtonAndMusicButton(int radioGroupID) {
        ((RadioButton) musicRadioGroup.getChildAt(radioGroupID)).setChecked(true);
        Music.MUSIC_TYPE type = radioGroupIndexToMusicType(musicRadioGroup);
        setMusicButtonDrawable(type);
    }

    @Override
    public void setOnMusicRadioButton() {
        btnMusic.setBackgroundResource(R.drawable.ic_radio_pressed);
    }

    @Override
    public void setOffMusicRadioButton() {
        btnMusic.setBackgroundResource(R.drawable.ic_radio);
    }

    private void setMusicButtonDrawable(Music.MUSIC_TYPE type) {
        switch (type) {
            case MUSIC_FILE:
                btnMusic.setBackgroundResource(R.drawable.ic_music_file);
                break;
            case RADIO:
                btnMusic.setBackgroundResource(R.drawable.ic_radio);
                break;
            case RINGTONE:
                btnMusic.setBackgroundResource(R.drawable.ic_ringtone);
                break;
            default:
                btnMusic.setBackgroundResource(R.drawable.ic_radio);
                break;
        }
    }

    @Override
    public void setTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void setDaysCheckboxChecked(boolean isChecked) {
        checkBoxRepeate.setChecked(isChecked);
    }

    @Override
    public void setDaysButtons(SparseIntArray ids) {
        if (checkBoxRepeate.isChecked()) {
            rlDays.setVisibility(View.VISIBLE);
            for (int requestCode = 0; requestCode < ids.size(); ++requestCode) {
                for (int btnID = 0; btnID < tbDaysOfWeek.size(); ++btnID) {
                    int key = tbDaysOfWeek.keyAt(btnID);
                    if (ids.keyAt(requestCode) == tbDaysOfWeek.get(key))
                        try {
                            ((ToggleButton) ButterKnife.findById(getView(), key))
                                    .setChecked(true);
                        } catch (NullPointerException e) {
                            Log.v("view is null");
                        }
                }
            }
        } else {
            rlDays.setVisibility(View.GONE);
        }
    }

    @Override
    public void showDays() {
        rlDays.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDays() {
        for (int i = 0; i < rlDays.getChildCount(); ++i)
            ((ToggleButton) rlDays.getChildAt(i)).setChecked(false);
        rlDays.setVisibility(View.GONE);
    }

    @Override
    public void setIsSnoozeEnable(boolean isSnoozeEnable) {
        checkBoxSnooze.setChecked(isSnoozeEnable);
    }

    @Override
    public void setIsMathEnable(boolean isMathEnable) {
        checkBoxMath.setChecked(isMathEnable);
    }

    @Override
    public void setName(String name) {
        etName.setText(name);
        etName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                etName.setSelection(etName.getText().length());
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        presenter.afterTextChanged(s);
    }

    @Override
    public void onClick(View view) {
        Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.toggle_days);
        view.startAnimation(bounce);

        boolean on = ((ToggleButton) view).isChecked();
        if (on)
            presenter.setDayOn(tbDaysOfWeek.get(view.getId()));
        else
            presenter.setDayOff(tbDaysOfWeek.get(view.getId()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v("requestCode = " + requestCode);
        switch (requestCode) {
            case Consts.REQUEST_CODE_FILE_CHOSER:
//                if (resultCode == RESULT_OK) {
//                    if (data != null) {
//                        // Get the URI of the selected file
//                        final Uri uri = data.getData();
//                        try {
//                            // Get the file g_path from the URI
//                            String path = FileUtils.getPath(getContext(), uri);
//                            if (MusicFile.checkExtension(path)) {
//                                music.setMusicFile(path);
//                                model.setMusic(music);
//                            } else {
//                                settingsView.showToastNoMusicFile();
//                                settingsView.setMusicButton(Music.MUSIC_TYPE.RINGTONE.getCode());
//                            }
//                        } catch (Exception e) {
//                            Log.v("FilePlayer select error");
//                        }
//                    }
//                } else
//                    settingsView.setMusicButton(model.getMusicType());
                break;
            case Consts.REQUEST_CODE_DEFAULT_RINGTONE:
                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//                    if (uri != null) {
//                        music.setDefaultRingtone(uri.toString());
//                        model.setMusic(music);
//                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopPlaying();
    }
}
