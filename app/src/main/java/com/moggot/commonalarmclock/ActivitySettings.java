package com.moggot.commonalarmclock;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
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
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;
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
    private SparseArray<Byte> tbDaysOfWeek;
    private CheckBox checkBoxMath, checkBoxSnooze;
    private RadioGroup rgMusic;
    private ImageView btnMusic;

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

        tbDaysOfWeek = new SparseArray<>();
        tbDaysOfWeek.put(R.id.tbMonday, Consts.DAYS.MONDAY.getCode());
        tbDaysOfWeek.put(R.id.tbTuesday, Consts.DAYS.TUESDAY.getCode());
        tbDaysOfWeek.put(R.id.tbWednesday, Consts.DAYS.WEDNESDAY.getCode());
        tbDaysOfWeek.put(R.id.tbThursday, Consts.DAYS.THURSDAY.getCode());
        tbDaysOfWeek.put(R.id.tbFriday, Consts.DAYS.FRIDAY.getCode());
        tbDaysOfWeek.put(R.id.tbSaturday, Consts.DAYS.SATURDAY.getCode());
        tbDaysOfWeek.put(R.id.tbSunday, Consts.DAYS.SUNDAY.getCode());
        for (int i = 0; i < tbDaysOfWeek.size(); ++i) {
            (findViewById(tbDaysOfWeek.keyAt(i))).setOnClickListener(this);
        }

        EditText etName = (EditText) findViewById(R.id.etAlarmName);
        etName.addTextChangedListener(textWatcher);

        rgMusic = (RadioGroup) findViewById(R.id.rgMusicType);
        rgMusic.setOnCheckedChangeListener(rgListener);

        btnMusic = (ImageView) findViewById(R.id.btnMusic);

        ImageView btnSave = (ImageView) findViewById(R.id.ivSaveAlarm);

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
                finish();
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
            if (ids.get(Consts.DAYS.TOMORROW.getCode()) != 0)
                ids.clear();
            int requestCode = db.getRandomRequestCode();
            ids.put(tbDaysOfWeek.get(v.getId()), requestCode);
        } else {
            ids.delete(tbDaysOfWeek.get(v.getId()));
        }

        if (ids.size() == 0)
            ids.put(Consts.DAYS.TOMORROW.getCode(), db.getRandomRequestCode());

        alarm.setIDs(ids);
        Log.v(LOG_TAG, "ids = " + ids);


//        for (int i = 0; i < tbDaysOfWeek.size(); ++i) {
//            int temp = days & tbDaysOfWeek.get(tbDaysOfWeek.keyAt(i));
//            int t_temp = temp ^ tbDaysOfWeek.get(tbDaysOfWeek.keyAt(i));
//            ((ToggleButton) findViewById(tbDaysOfWeek.keyAt(i)))
//                    .setChecked(temp > 0 && t_temp == 0);
//        }
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
                    btnMusic.setImageResource(R.drawable.ic_library_music_black_24px);
                    showChooser();
                    break;
                case R.id.rbRadio:
                    btnMusic.setImageResource(R.drawable.ic_radio_black_24px);
                    alarm.setMusic(Consts.MUSIC_TYPE.RADIO.getType(), Consts.DATA_RADIO);
                    break;
                case R.id.rbRingtones:
                    btnMusic.setImageResource(R.drawable.ic_music_note_black_24px);
                    alarm.setMusic(Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType(), Consts.DATA_DEFAULT_RINGTONE);
                    break;
            }

        }
    };

    public void onClickMusic(View view) {
        switch (rgMusic.getCheckedRadioButtonId()) {
            case R.id.rbFile:
                showChooser();
                break;
            case R.id.rbRadio:
                Intent musicIntent = new Intent(this, MusicService.class);
                musicIntent.putExtra(Consts.EXTRA_TYPE, alarm.getMusicType());
                musicIntent.putExtra(Consts.EXTRA_PATH, alarm.getMusicPath());
                if (isMusicPlaying) {
                    isMusicPlaying = false;
                    stopService(musicIntent);
                } else {
                    isMusicPlaying = true;
                    startService(musicIntent);
                }
                break;
            case R.id.rbRingtones:
                showRingtones();
                break;
        }
    }

    public void showRingtones() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        this.startActivityForResult(intent, Consts.REQUEST_CODE_DEFAULT_RINGTONE);
    }

    private void showChooser() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target,
                getString(R.string.app_name));
        try {
            startActivityForResult(intent, Consts.REQUEST_CODE_FILE_CHOSER);
        } catch (ActivityNotFoundException e) {
        }
    }

    private Alarm createAlarm() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());

        SparseIntArray ids = new SparseIntArray();
        Consts.DAYS days = Consts.DAYS.TOMORROW;
        int requstCode = db.getRandomRequestCode();
        Log.v(LOG_TAG, "id = " + requstCode);
        ids.put(days.getCode(), requstCode);
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
                    ((RadioButton) rgMusic.getChildAt(Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType())).setChecked(true);
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
