package com.moggot.commonalarmclock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenter;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenterImpl;
import com.moggot.commonalarmclock.mvp.settings.SettingsView;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.music.Music;

import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class ActivitySettings extends AppCompatActivity implements
        SettingsView
        , OnClickListener
        , TextWatcher {

    private static final String LOG_TAG = ActivitySettings.class.getSimpleName();

    private SparseIntArray tbDaysOfWeek;

    private SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Analysis analysis = new Analysis(this);
        analysis.sendScreenName(this.getClass().getSimpleName());

        mapDayButtonToCalendarDay();

        long id = getIntent().getLongExtra(Consts.EXTRA_ID, Consts.NO_ID);
        presenter = new SettingsPresenterImpl(this);
        presenter.initialize(id);

    }

    private void mapDayButtonToCalendarDay() {
        this.tbDaysOfWeek = new SparseIntArray();
        tbDaysOfWeek.put(R.id.tbMonday, Calendar.MONDAY);
        tbDaysOfWeek.put(R.id.tbTuesday, Calendar.TUESDAY);
        tbDaysOfWeek.put(R.id.tbWednesday, Calendar.WEDNESDAY);
        tbDaysOfWeek.put(R.id.tbThursday, Calendar.THURSDAY);
        tbDaysOfWeek.put(R.id.tbFriday, Calendar.FRIDAY);
        tbDaysOfWeek.put(R.id.tbSaturday, Calendar.SATURDAY);
        tbDaysOfWeek.put(R.id.tbSunday, Calendar.SUNDAY);
        for (int i = 0; i < tbDaysOfWeek.size(); ++i) {
            (findViewById(tbDaysOfWeek.keyAt(i))).setOnClickListener(this);
        }
    }

    public void onClickSave(View view) {
        presenter.onClickSave(view);
    }

    View.OnClickListener timeListiner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.timerPickerOnClick();
        }
    };

    public void onClick(View view) {
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.toggle_days);
        view.startAnimation(bounce);

        presenter.onClickDay(view, tbDaysOfWeek.get(view.getId()));
    }

    CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            presenter.onCheckedChangedCheckBox(buttonView, isChecked);
        }
    };

    RadioGroup.OnCheckedChangeListener rgListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
            presenter.onCheckedChangedRadioGroup(radioGroup, checkedID);
        }
    };

    public void onClickMusic(View view) {
        presenter.onClickButtonMusic(view, ((RadioGroup) findViewById(R.id.rgMusicType)).getCheckedRadioButtonId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setupViews() {
        ((RadioGroup) findViewById(R.id.rgMusicType)).setOnCheckedChangeListener(rgListener);
        ((EditText) findViewById(R.id.etAlarmName)).addTextChangedListener(this);
        findViewById(R.id.tvAlarmTime).setOnClickListener(timeListiner);
        ((CheckBox) findViewById(R.id.checkBoxMath)).setOnCheckedChangeListener(checkBoxListener);
        ((CheckBox) findViewById(R.id.checkBoxSnooze)).setOnCheckedChangeListener(checkBoxListener);
        ((CheckBox) findViewById(R.id.checkBoxRepeat)).setOnCheckedChangeListener(checkBoxListener);
    }

    @Override
    public void setTime(String time) {
        ((TextView) findViewById(R.id.tvAlarmTime)).setText(time);
    }

    @Override
    public void setDaysCheckbox(boolean isChecked) {
        ((CheckBox) findViewById(R.id.checkBoxRepeat)).setChecked(isChecked);
    }

    @Override
    public void setDays(SparseIntArray ids) {
        if (((CheckBox) findViewById(R.id.checkBoxRepeat)).isChecked()) {
            findViewById(R.id.rlDays).setVisibility(View.VISIBLE);
            for (int requestCode = 0; requestCode < ids.size(); ++requestCode) {
                for (int btnID = 0; btnID < tbDaysOfWeek.size(); ++btnID) {
                    int key = tbDaysOfWeek.keyAt(btnID);
                    if (ids.keyAt(requestCode) == tbDaysOfWeek.get(key))
                        ((ToggleButton) findViewById(key))
                                .setChecked(true);
                }
            }
        } else {
            findViewById(R.id.rlDays).setVisibility(View.GONE);
        }
    }

    @Override
    public void showDays() {
        findViewById(R.id.rlDays).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDays() {
        RelativeLayout rlDays = ((RelativeLayout) findViewById(R.id.rlDays));
        for (int i = 0; i < rlDays.getChildCount(); ++i)
            ((ToggleButton) rlDays.getChildAt(i)).setChecked(false);
        findViewById(R.id.rlDays).setVisibility(View.GONE);
    }

    @Override
    public void setIsSnoozeEnable(boolean isSnoozeEnable) {
        ((CheckBox) findViewById(R.id.checkBoxSnooze)).setChecked(isSnoozeEnable);
    }

    @Override
    public void setIsMathEnable(boolean isMathEnable) {
        ((CheckBox) findViewById(R.id.checkBoxMath)).setChecked(isMathEnable);
    }

    @Override
    public void setName(String name) {
        final EditText etName = (EditText) findViewById(R.id.etAlarmName);
        etName.setText(name);
        etName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                etName.setSelection(etName.getText().length());
            }
        });
    }

    @Override
    public void setMusicButton(int musicType) {
        ((RadioButton) ((RadioGroup) findViewById(R.id.rgMusicType)).getChildAt(musicType)).setChecked(true);
        setMusicButtonDrawable(musicType);
    }

    @Override
    public void setButtonRadioDrawable(boolean isPlaying) {
        if (isPlaying)
            findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_radio_pressed);
        else
            findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_radio);
    }

    @Override
    public void showToastNoMusicFile() {
        Toast.makeText(this, R.string.not_music_file, Toast.LENGTH_SHORT).show();
    }

    private void setMusicButtonDrawable(int musicType) {
        Music.MUSIC_TYPE type = Music.MUSIC_TYPE.fromInteger(musicType);
        switch (type) {
            case MUSIC_FILE:
                findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_music_file);
                break;
            case RADIO:
                findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_radio);
                break;
            case DEFAULT_RINGTONE:
                findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_note);
                break;
            default:
                findViewById(R.id.btnMusic).setBackgroundResource(R.drawable.ic_radio);
                break;
        }
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

}
