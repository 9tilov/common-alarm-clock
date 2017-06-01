package com.moggot.commonalarmclock.mvp.settings;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.MusicFile;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.animation.MusicFileAnimationBounce;
import com.moggot.commonalarmclock.animation.RadioAnimationBounce;
import com.moggot.commonalarmclock.animation.RingtoneAnimationBounce;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.music.MusicService;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class SettingsPresenterImpl implements SettingsPresenter {

    private static final String LOG_TAG = SettingsPresenterImpl.class.getSimpleName();

    private SettingsView view;
    private SettingsModel model;

    private boolean isMusicPlaying;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
        this.isMusicPlaying = false;
    }

    @Override
    public void initialize(long id) {
        this.model = new SettingsModelImpl(view.getContext());
        model.loadAlarm(id);

        view.setupViews();

        view.setTime(model.getDateAsString());
        view.setDaysCheckbox(model.getDaysCheckboxState());
        view.setDays(model.getRepeateIDs());
        view.setIsSnoozeEnable(model.getIsSnoozeEnable());
        view.setIsMathEnable(model.getIsMathEnable());
        view.setName(model.getName());
        view.setMusicButton(model.getMusicCode());
    }

    @Override
    public void saveAlarm() {
        model.saveAlarm();
    }

    @Override
    public void timerPickerOnClick() {
        TimePickerDialog timePicker = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                setSelectedTime(selectedHour, selectedMinute);
            }
        }, getCurrentHour(), getCurrentMinute(), true);
        timePicker.show();
    }

    @Override
    public void onClickDay(View view, int dayCode) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on)
            model.setDayOn(dayCode);
        else
            model.setDayOff(dayCode);
    }

    @Override
    public void onCheckedChangedCheckBox(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.checkBoxSnooze)
            model.setIsSnoozeEnable(isChecked);
        if (buttonView.getId() == R.id.checkBoxMath)
            model.setIsMathEnable(isChecked);
        if (buttonView.getId() == R.id.checkBoxRepeat) {
            if (isChecked)
                view.showDays();
            else {
                view.hideDays();
                model.setTomorrowDay();
            }
        }
    }

    @Override
    public void onCheckedChangedRadioGroup(RadioGroup radioGroup, int checkedID) {
        Music music = new Music(view.getContext());
        switch (checkedID) {
            case R.id.rbFile:
                if (isMusicPlaying) {
                    stopPlayingRadio();
                }
                view.setMusicButton(Music.MUSIC_TYPE.MUSIC_FILE.getCode());
                break;
            case R.id.rbRadio:
                if (model.isNetworkAvailable()) {
                    view.setMusicButton(Music.MUSIC_TYPE.RADIO.getCode());
                    music.setInternetRadio();
                } else
                    view.setMusicButton(model.getMusicCode());
                break;
            case R.id.rbRingtones:
                if (isMusicPlaying) {
                    stopPlayingRadio();
                }
                view.setMusicButton(Music.MUSIC_TYPE.DEFAULT_RINGTONE.getCode());
                music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                break;
            default:
                music.setInternetRadio();
                break;
        }
        model.setMusic(music);
    }

    @Override
    public void onClickButtonMusic(View view, int radioButtonID) {
        AnimationBounce animationBounce;
        Context context = view.getContext();
        switch (radioButtonID) {
            case R.id.rbFile:
                animationBounce = new MusicFileAnimationBounce(context);
                break;
            case R.id.rbRadio:
                if (isMusicPlaying)
                    stopPlayingRadio();
                else
                    startPlayingRadio();
                animationBounce = new RadioAnimationBounce(context);
                break;
            case R.id.rbRingtones:
                animationBounce = new RingtoneAnimationBounce(context);
                break;
            default:
                animationBounce = new RingtoneAnimationBounce(context);
        }
        animationBounce.animate(view);
    }

    @Override
    public void afterTextChanged(Editable s) {
        model.setName(s.toString());
    }

    private void startPlayingRadio() {
        Intent musicIntent = new Intent(view.getContext(), MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_MUSIC, new Music(view.getContext()));
        isMusicPlaying = true;
        view.setButtonRadioDrawable(true);
        view.getContext().startService(musicIntent);
    }

    private void stopPlayingRadio() {
        Intent musicIntent = new Intent(view.getContext(), MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_MUSIC, new Music(view.getContext()));
        isMusicPlaying = false;
        view.setButtonRadioDrawable(false);
        view.getContext().stopService(musicIntent);
    }

    private int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());
        return calendar.get(Calendar.MINUTE);
    }

    private void setSelectedTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date date = new Date(calendar.getTimeInMillis());
        model.setDate(date);
        view.setTime(model.getDateAsString());
    }

    @Override
    public void onDestroy() {
        Intent musicIntent = new Intent(view.getContext(), MusicService.class);
        view.getContext().stopService(musicIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Music music = new Music(view.getContext());
        switch (requestCode) {
            case Consts.REQUEST_CODE_FILE_CHOSER:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file g_path from the URI
                            String path = FileUtils.getPath(view.getContext(), uri);
                            if (MusicFile.checkExtension(path)) {
                                music.setMusicFile(path);
                                model.setMusic(music);
                            } else {
                                view.showToastNoMusicFile();
                                view.setMusicButton(Music.MUSIC_TYPE.DEFAULT_RINGTONE.getCode());
                            }
                        } catch (Exception e) {
                            Log.e("FileSelector", "File select error", e);
                        }
                    }
                } else
                    view.setMusicButton(model.getMusicCode());
                break;
            case Consts.REQUEST_CODE_DEFAULT_RINGTONE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        music.setDefaultRingtone(uri.toString());
                        model.setMusic(music);
                    }
                }
                break;
        }
    }
}