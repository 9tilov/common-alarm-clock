package com.moggot.commonalarmclock;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
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
import com.moggot.commonalarmclock.Observer.AlarmData;
import com.moggot.commonalarmclock.Observer.SettingsDisplay;
import com.moggot.commonalarmclock.alarm.Alarm;

import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        final AlarmData alarmData = new AlarmData();
        SettingsDisplay settingsDisplay = new SettingsDisplay(this, alarmData);
        alarmData.setAlarm(alarm);
        settingsDisplay.display();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 0)
                    db.addAlarm(alarm);
                else
                    db.editAlarm(alarm);
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
        int days = alarm.getDays();
        if (on) {
            days = days | tbDaysOfWeek.get(v.getId());
        } else {
            days = days & ~tbDaysOfWeek.get(v.getId());
        }

        alarm.setDays(days);

        for (int i = 0; i < tbDaysOfWeek.size(); ++i) {
            int temp = days & tbDaysOfWeek.get(tbDaysOfWeek.keyAt(i));
            int t_temp = temp ^ tbDaysOfWeek.get(tbDaysOfWeek.keyAt(i));
            ((ToggleButton) findViewById(tbDaysOfWeek.keyAt(i)))
                    .setChecked(temp > 0 && t_temp == 0);
        }
    }

    CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                if (buttonView.getId() == R.id.checkBoxSnooze)
                    alarm.setIsMathEnable(true);
                if (buttonView.getId() == R.id.checkBoxMath)
                    alarm.setIsSnoozeEnable(true);
            } else {
                if (buttonView.getId() == R.id.checkBoxMath)
                    alarm.setIsMathEnable(false);
                if (buttonView.getId() == R.id.checkBoxSnooze)
                    alarm.setIsSnoozeEnable(false);
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

        List<Integer> requestCodes = new ArrayList<>();
        requestCodes.add(db.getRandomRequestCode());
        String requestCodesStr = new Gson().toJson(requestCodes);
        Consts.DAYS days = Consts.DAYS.TOMORROW;

        Consts.MUSIC_TYPE musicType = Consts.MUSIC_TYPE.RADIO;

        return new Alarm(null, date, requestCodesStr, days.getCode(),
                checkBoxSnooze.isChecked(), checkBoxMath.isChecked(), "", Consts.DATA_RADIO,
                musicType.getType(), true);
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

    private void notMusicFile() {
        Toast.makeText(this, R.string.not_music_file,
                Toast.LENGTH_SHORT).show();
    }


}
