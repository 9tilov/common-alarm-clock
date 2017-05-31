package com.moggot.commonalarmclock;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.mvp.settings.SettingsModelImpl;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenter;
import com.moggot.commonalarmclock.mvp.settings.SettingsPresenterImpl;
import com.moggot.commonalarmclock.mvp.settings.SettingsView;
import com.moggot.commonalarmclock.analytics.Analysis;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.animation.MusicFileAnimationBounce;
import com.moggot.commonalarmclock.animation.RadioAnimationBounce;
import com.moggot.commonalarmclock.animation.RingtoneAnimationBounce;
import com.moggot.commonalarmclock.animation.SaveAlarmAnimationBounce;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.music.MusicService;

import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class ActivitySettings extends AppCompatActivity implements SettingsView, OnClickListener {

    private static final String LOG_TAG = ActivitySettings.class.getSimpleName();

    private TextView tvAlarmTime;
    private SparseIntArray tbDaysOfWeek;
    private RadioGroup rgMusic;
    private Button btnMusic;

    private boolean isMusicPlaying = false;

    private SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Analysis analysis = new Analysis(this);
        analysis.start();

        tbDaysOfWeek = new SparseIntArray();
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

        setupMVP();
        final long id = getIntent().getLongExtra(Consts.EXTRA_ID, Consts.NO_ID);
        presenter.setSettings(id);

        tvAlarmTime = (TextView) findViewById(R.id.tvAlarmTime);
        tvAlarmTime.setOnClickListener(timeListiner);

        CheckBox checkBoxMath = (CheckBox) findViewById(R.id.checkBoxMath);
        checkBoxMath.setOnCheckedChangeListener(checkBoxListener);

        CheckBox checkBoxSnooze = (CheckBox) findViewById(R.id.checkBoxSnooze);
        checkBoxSnooze.setOnCheckedChangeListener(checkBoxListener);

        CheckBox checkBoxRepeat = (CheckBox) findViewById(R.id.checkBoxRepeat);
        checkBoxRepeat.setOnCheckedChangeListener(checkBoxListener);

        EditText etName = (EditText) findViewById(R.id.etAlarmName);
        etName.addTextChangedListener(textWatcher);

        rgMusic = (RadioGroup) findViewById(R.id.rgMusicType);
        rgMusic.setOnCheckedChangeListener(rgListener);

        btnMusic = (Button) findViewById(R.id.btnMusic);

        Button btnSave = (Button) findViewById(R.id.btnSaveAlarm);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (id == 0)
//                    db.saveAlarm(alarm);
//                else {
//                    Alarm loadedAlarm = db.getAlarm(id);
//                    if (!compareDays(loadedAlarm.getRepeatAlarmIDs(), alarm.getRepeatAlarmIDs())) {
//                        AlarmContext alarmContext = new AlarmContext(loadedAlarm, getApplicationContext());
//                        AlarmScheduler alarmManager = new AlarmScheduler();
//                        alarmManager.cancelAlarm(alarmContext);
//                    }
//                    db.updateAlarm(alarm);
//                }
//                AlarmContext alarmContext = new AlarmContext(alarm, getApplicationContext());
//                AlarmScheduler alarmManager = new AlarmScheduler();
//                alarmManager.setAlarm(alarmContext);

//                presenter.saveAlarm();
                presenter.saveAlarm();
                AnimationBounce animationBounce = new SaveAlarmAnimationBounce(ActivitySettings.this);
                animationBounce.animate(view);
            }
        });

    }

    private void setupMVP() {
        SettingsPresenterImpl presenter = new SettingsPresenterImpl(this);
        SettingsModelImpl model = new SettingsModelImpl(getApplicationContext());
        presenter.setModel(model);
        this.presenter = presenter;
    }

    View.OnClickListener timeListiner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(presenter.getDate());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePicker = new TimePickerDialog(ActivitySettings.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    calendar.set(Calendar.SECOND, 0);
                    Date date = new Date(calendar.getTimeInMillis());
                    presenter.setDate(date);
                }
            }, hour, minute, true);
            timePicker.show();
        }
    };

    public void onClick(View v) {
        boolean on = ((ToggleButton) v).isChecked();
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.toggle_days);
        v.startAnimation(bounce);
        if (on)
            presenter.setDayOn(tbDaysOfWeek.get(v.getId()));
        else
            presenter.setDayOff(tbDaysOfWeek.get(v.getId()));
    }

    CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (buttonView.getId() == R.id.checkBoxSnooze)
                presenter.setSnoozeCheckbox(isChecked);
            if (buttonView.getId() == R.id.checkBoxMath)
                presenter.setMathCheckbox(isChecked);
            if (buttonView.getId() == R.id.checkBoxRepeat) {
                if (isChecked)
                    ((RelativeLayout) findViewById(R.id.rlDays)).setVisibility(View.VISIBLE);
                else {
                    RelativeLayout rlDays = ((RelativeLayout) findViewById(R.id.rlDays));
                    for (int i = 0; i < rlDays.getChildCount(); ++i) {
                        ((ToggleButton) rlDays.getChildAt(i)).setChecked(false);
                        presenter.setTomorrowDay();
                    }
                    ((RelativeLayout) findViewById(R.id.rlDays)).setVisibility(View.GONE);
                }
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            presenter.setName(s.toString());
        }
    };

    RadioGroup.OnCheckedChangeListener rgListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
            Music music = new Music(ActivitySettings.this);
            switch (checkedID) {
                case R.id.rbFile:
                    if (isMusicPlaying) {
                        isMusicPlaying = false;
                        Intent dummyIntent = new Intent(ActivitySettings.this, MusicService.class);
                        stopService(dummyIntent);
                    }
                    btnMusic.setBackgroundResource(R.drawable.ic_music_file);
                    break;
                case R.id.rbRadio:
                    if (isNetworkAvailable()) {
                        btnMusic.setBackgroundResource(R.drawable.ic_radio);
                        music.setInternetRadio();
                    } else {
                        internetUnavailable();
                        ((RadioButton) rgMusic.getChildAt(presenter.getMusicCode())).setChecked(true);
                    }
                    break;
                case R.id.rbRingtones:
                    if (isMusicPlaying) {
                        isMusicPlaying = false;
                        Intent dummyIntent = new Intent(ActivitySettings.this, MusicService.class);
                        stopService(dummyIntent);
                    }
                    btnMusic.setBackgroundResource(R.drawable.ic_note);
                    music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                    break;
                default:
                    music.setInternetRadio();
                    break;
            }
            presenter.setMusic(music);
        }
    };

    public void onClickMusic(View view) {
        AnimationBounce animationBounce;
        switch (rgMusic.getCheckedRadioButtonId()) {
            case R.id.rbFile:
                animationBounce = new MusicFileAnimationBounce(this);
                animationBounce.animate(view);
                break;
            case R.id.rbRadio:
                Intent musicIntent = new Intent(this, MusicService.class);
                musicIntent.putExtra(Consts.EXTRA_MUSIC, new Music(this));
                if (isMusicPlaying) {
                    isMusicPlaying = false;
                    btnMusic.setBackgroundResource(R.drawable.ic_radio);
                    stopService(musicIntent);
                } else {
                    isMusicPlaying = true;
                    btnMusic.setBackgroundResource(R.drawable.ic_radio_pressed);
                    startService(musicIntent);
                }

                animationBounce = new RadioAnimationBounce(this);
                animationBounce.animate(view);
                break;
            case R.id.rbRingtones:
                animationBounce = new RingtoneAnimationBounce(this);
                animationBounce.animate(view);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Music music = new Music(this);
        switch (requestCode) {
            case Consts.REQUEST_CODE_FILE_CHOSER:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file g_path from the URI
                            String path = FileUtils.getPath(this, uri);
                            if (MusicFile.checkExtension(path)) {
                                music.setMusicFile(path);
                                presenter.setMusic(music);
                            } else {
                                notMusicFile();
                                ((RadioButton) rgMusic.getChildAt(Music.MUSIC_TYPE.DEFAULT_RINGTONE.getCode())).setChecked(true);
                            }
                        } catch (Exception e) {
                            Log.e("FileSelector", "File select error", e);
                        }
                    }
                } else {
                    ((RadioButton) rgMusic.getChildAt(presenter.getMusicCode())).setChecked(true);
                }
                break;
            case Consts.REQUEST_CODE_DEFAULT_RINGTONE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        music.setDefaultRingtone(uri.toString());
                        presenter.setMusic(music);
                    }
                }
                break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void notMusicFile() {
        Toast.makeText(this, R.string.not_music_file,
                Toast.LENGTH_SHORT).show();
    }

    private void internetUnavailable() {
        Toast.makeText(this,
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent musicIntent = new Intent(ActivitySettings.this, MusicService.class);
        stopService(musicIntent);
    }

    private static boolean compareDays(SparseIntArray first, SparseIntArray second) {
        // compare null
        if (first == null) {
            return (second == null);
        }
        if (second == null) {
            return false;
        }

        // compare count
        int count = first.size();
        if (second.size() != count) {
            return false;
        }

        // for each pair
        for (int index = 0; index < count; ++index) {
            // compare key
            int key = first.keyAt(index);
            if (key != second.keyAt(index)) {
                return false;
            }

            // compare value
            int value = first.valueAt(index);
            if (second.valueAt(index) != value) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
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
            ((RelativeLayout) findViewById(R.id.rlDays)).setVisibility(View.VISIBLE);
            for (int requestCode = 0; requestCode < ids.size(); ++requestCode) {
                for (int btnID = 0; btnID < tbDaysOfWeek.size(); ++btnID) {
                    int key = tbDaysOfWeek.keyAt(btnID);
                    if (ids.keyAt(requestCode) == tbDaysOfWeek.get(key))
                        ((ToggleButton) findViewById(key))
                                .setChecked(true);
                }
            }
        } else {
            ((RelativeLayout) findViewById(R.id.rlDays)).setVisibility(View.GONE);
        }
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
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etName.setSelection(etName.getText().length());
                }
            }
        });
    }

    @Override
    public void setMusicRadioButton(int musicType) {
        ((RadioButton) ((RadioGroup) findViewById(R.id.rgMusicType)).getChildAt(musicType)).setChecked(true);
    }
}
