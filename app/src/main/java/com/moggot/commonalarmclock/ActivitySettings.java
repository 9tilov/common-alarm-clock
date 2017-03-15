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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.animation.Animation;
import com.moggot.commonalarmclock.animation.MusicFileAnimation;
import com.moggot.commonalarmclock.animation.RadioAnimation;
import com.moggot.commonalarmclock.animation.RingtoneAnimation;
import com.moggot.commonalarmclock.animation.SaveAlarmAnimation;
import com.moggot.commonalarmclock.music.MusicService;
import com.moggot.commonalarmclock.observer.AlarmData;
import com.moggot.commonalarmclock.observer.SettingsDisplay;
import com.moggot.commonalarmclock.alarm.Alarm;

import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by toor on 06.03.17.
 */

public class ActivitySettings extends AppCompatActivity implements OnClickListener {

    private static final String LOG_TAG = "ActivitySettings";

    private TextView tvAlarmTime;
    private SparseIntArray tbDaysOfWeek;
    private CheckBox checkBoxMath, checkBoxSnooze;
    private RadioGroup rgMusic;
    private Button btnMusic;

    private Alarm alarm;
    private DataBase db;

    private boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvAlarmTime = (TextView) findViewById(R.id.tvAlarmTime);
        tvAlarmTime.setOnClickListener(timeListiner);

        checkBoxMath = (CheckBox) findViewById(R.id.checkBoxMath);
        checkBoxMath.setOnCheckedChangeListener(checkBoxListener);

        checkBoxSnooze = (CheckBox) findViewById(R.id.checkBoxSnooze);
        checkBoxSnooze.setOnCheckedChangeListener(checkBoxListener);

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

        EditText etName = (EditText) findViewById(R.id.etAlarmName);
        etName.addTextChangedListener(textWatcher);

        rgMusic = (RadioGroup) findViewById(R.id.rgMusicType);
        rgMusic.setOnCheckedChangeListener(rgListener);

        btnMusic = (Button) findViewById(R.id.btnMusic);

        Button btnSave = (Button) findViewById(R.id.btnSaveAlarm);

        db = new DataBase(this);

        final long id = getIntent().getLongExtra(Consts.EXTRA_ID, 0);
        if (id == 0)
            alarm = createAlarm();
        else
            alarm = db.getAlarm(id);

        Log.v(LOG_TAG, "id_load = " + alarm.getIDs());

        final AlarmData alarmData = new AlarmData();
        SettingsDisplay settingsDisplay = new SettingsDisplay(this, alarmData);
        alarmData.setAlarm(alarm);
        settingsDisplay.display();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 0)
                    db.addAlarm(alarm);
                else {
                    Alarm loadedAlarm = db.getAlarm(id);
                    if (!compareDays(loadedAlarm.getIDs(), alarm.getIDs())) {
                        AlarmContext alarmContext = new AlarmContext(loadedAlarm, ActivitySettings.this);
                        AlarmManager alarmManager = new AlarmManager();
                        alarmManager.cancelAlarm(alarmContext);
                    }
                    db.editAlarm(alarm);
                }
                AlarmContext alarmContext = new AlarmContext(alarm, ActivitySettings.this);
                AlarmManager alarmManager = new AlarmManager();
                alarmManager.setAlarm(alarmContext);

                Log.v(LOG_TAG, "path = " + alarm.getMusicPath() + "  type = " + alarm.getMusicType());

                Animation animation = new SaveAlarmAnimation(ActivitySettings.this);
                animation.animate(view);
            }
        });

    }

    View.OnClickListener timeListiner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog timePicker = new TimePickerDialog(ActivitySettings.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    calendar.set(Calendar.SECOND, 0);
                    Date date = new Date(calendar.getTimeInMillis());
                    alarm.setDate(date);
                    tvAlarmTime.setText(
                            new StringBuilder()
                                    .append(pad(selectedHour)).append(":")
                                    .append(pad(selectedMinute)));
                }
            }, alarm.getHour(), alarm.getMinute(), true);//Yes 24 hour time
            timePicker.show();
        }
    };

    public void onClick(View v) {
        boolean on = ((ToggleButton) v).isChecked();
        SparseIntArray ids = alarm.getIDs();
        if (on) {
            if (ids.get(Consts.TOMORROW) != 0)
                ids.clear();
            int requestCode = db.getRandomRequestCode();
            ids.put(tbDaysOfWeek.get(v.getId()), requestCode);
        } else {
            ids.delete(tbDaysOfWeek.get(v.getId()));
        }

        if (ids.size() == 0)
            ids.put(Consts.TOMORROW, db.getRandomRequestCode());

        alarm.setIDs(ids);
        Log.v(LOG_TAG, "ids = " + ids);

    }

    CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                if (buttonView.getId() == R.id.checkBoxSnooze)
                    alarm.setIsSnoozeEnable(true);
                if (buttonView.getId() == R.id.checkBoxMath)
                    alarm.setIsMathEnable(true);
            } else {
                if (buttonView.getId() == R.id.checkBoxSnooze)
                    alarm.setIsSnoozeEnable(false);
                if (buttonView.getId() == R.id.checkBoxMath)
                    alarm.setIsMathEnable(false);
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
            alarm.setName(s.toString());
        }
    };

    RadioGroup.OnCheckedChangeListener rgListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
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
                    btnMusic.setBackgroundResource(R.drawable.ic_radio);
                    alarm.setMusic(Consts.MUSIC_TYPE.RADIO.getType(), Consts.DATA_RADIO);
                    break;
                case R.id.rbRingtones:
                    if (isMusicPlaying) {
                        isMusicPlaying = false;
                        Intent dummyIntent = new Intent(ActivitySettings.this, MusicService.class);
                        stopService(dummyIntent);
                    }
                    btnMusic.setBackgroundResource(R.drawable.ic_note);
                    alarm.setMusic(Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType(), Consts.DATA_DEFAULT_RINGTONE);
                    break;
            }

        }
    };

    public void onClickMusic(View view) {
        Animation animation;
        switch (rgMusic.getCheckedRadioButtonId()) {
            case R.id.rbFile:
                animation = new MusicFileAnimation(this);
                animation.animate(view);
                break;
            case R.id.rbRadio:
                Intent musicIntent = new Intent(this, MusicService.class);
                musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
                musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
                if (isMusicPlaying) {
                    isMusicPlaying = false;
                    btnMusic.setBackgroundResource(R.drawable.ic_radio);
                    stopService(musicIntent);
                } else {
                    isMusicPlaying = true;
                    btnMusic.setBackgroundResource(R.drawable.ic_radio_pressed);
                    startService(musicIntent);
                }

                animation = new RadioAnimation(this);
                animation.animate(view);
                break;
            case R.id.rbRingtones:
                animation = new RingtoneAnimation(this);
                animation.animate(view);
                break;
        }
    }

    private Alarm createAlarm() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());

        SparseIntArray ids = new SparseIntArray();
        int requstCode = db.getRandomRequestCode();
        Log.v(LOG_TAG, "id = " + requstCode);
        ids.put(Consts.TOMORROW, requstCode);
        String requestCodesStr = new Gson().toJson(ids);

        int musicType;
        String path;
        if (isNetworkAvailable()) {
            musicType = Consts.MUSIC_TYPE.RADIO.getType();
            path = Consts.DATA_RADIO;
        } else {
            musicType = Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType();
            path = Consts.DATA_DEFAULT_RINGTONE;
        }

        return new Alarm(null, date, requestCodesStr, checkBoxSnooze.isChecked(),
                checkBoxMath.isChecked(), "", path, musicType, true);
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case Consts.REQUEST_CODE_FILE_CHOSER:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file g_path from the URI
                            String path = FileUtils.getPath(this, uri);
                            if (MusicFile.checkExtension(path)) {
                                alarm.setMusic(Consts.MUSIC_TYPE.MUSIC_FILE.getType(), path);
                            } else {
                                notMusicFile();
                                ((RadioButton) rgMusic.getChildAt(Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType())).setChecked(true);
                            }
                        } catch (Exception e) {
                            Log.e("FileSelector", "File select error", e);
                        }
                    }
                } else {
                    ((RadioButton) rgMusic.getChildAt(alarm.getMusicType())).setChecked(true);
                }
                break;
            case Consts.REQUEST_CODE_DEFAULT_RINGTONE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

                    if (uri != null)
                        alarm.setMusic(Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType(), uri.toString());
                }
                break;
        }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void notMusicFile() {
        Toast.makeText(this, R.string.not_music_file,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent musicIntent = new Intent(ActivitySettings.this, MusicService.class);
        stopService(musicIntent);
    }
}
