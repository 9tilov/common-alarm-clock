package com.moggot.commonalarmclock.mvp.settings;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.Log;
import com.moggot.commonalarmclock.MusicFile;
import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.animation.CallbackAnimation;
import com.moggot.commonalarmclock.music.Music;
import com.moggot.commonalarmclock.music.MusicService;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class SettingsPresenterImpl implements SettingsPresenter, CallbackAnimation {

    private SettingsView settingsView;
    private SettingsModel model;

    private boolean isMusicPlaying;

    @Inject
    public SettingsPresenterImpl(SettingsView settingsView) {
        this.settingsView = settingsView;
        this.isMusicPlaying = false;
    }

    @Override
    public void init(long id) {
        this.model = new SettingsModelImpl(settingsView.getContext());
        model.loadAlarm(id);

        settingsView.setTime(model.getDateAsString());
        settingsView.setDaysCheckbox(model.getDaysCheckboxState());
        settingsView.setDays(model.getRepeateIDs());
        settingsView.setIsSnoozeEnable(model.getIsSnoozeEnable());
        settingsView.setIsMathEnable(model.getIsMathEnable());
        settingsView.setName(model.getName());
        settingsView.setMusicButton(model.getMusicCode());
    }

    @Override
    public void onClickSave(View view) {
        model.saveAlarm();
        AnimationBounce animationBounce = new AnimationBounce(view);
        animationBounce.animate(view.getId(), this);
    }

    @Override
    public void timerPickerOnClick() {
        TimePickerDialog timePicker = new TimePickerDialog(settingsView.getContext()
                , (view, hourOfDay, minute) -> setSelectedTime(hourOfDay, minute)
                , getCurrentHour()
                , getCurrentMinute()
                , true);
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
                settingsView.showDays();
            else {
                settingsView.hideDays();
                model.setTomorrowDay();
            }
        }
    }

    @Override
    public void onCheckedChangedRadioGroup(RadioGroup radioGroup, int checkedID) {
        Music music = new Music(settingsView.getContext());
        switch (checkedID) {
            case R.id.rbFile:
                if (isMusicPlaying) {
                    stopPlayingRadio();
                }
                settingsView.setMusicButton(Music.MUSIC_TYPE.MUSIC_FILE.getCode());
                break;
            case R.id.rbRadio:
                if (model.isNetworkAvailable()) {
                    settingsView.setMusicButton(Music.MUSIC_TYPE.RADIO.getCode());
                    music.setInternetRadio();
                } else
                    settingsView.setMusicButton(model.getMusicCode());
                break;
            case R.id.rbRingtones:
                if (isMusicPlaying) {
                    stopPlayingRadio();
                }
                settingsView.setMusicButton(Music.MUSIC_TYPE.DEFAULT_RINGTONE.getCode());
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
        AnimationBounce animationBounce = new AnimationBounce(view);
        animationBounce.animate(radioButtonID, this);
    }


    @Override
    public void afterTextChanged(Editable s) {
        model.setName(s.toString());
    }

    private void startPlayingRadio() {
        Intent musicIntent = new Intent(settingsView.getContext(), MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_MUSIC, new Music(settingsView.getContext()));
        isMusicPlaying = true;
        settingsView.setButtonRadioDrawable(true);
        settingsView.getContext().startService(musicIntent);
    }

    private void stopPlayingRadio() {
        Intent musicIntent = new Intent(settingsView.getContext(), MusicService.class);
        musicIntent.putExtra(Consts.EXTRA_MUSIC, new Music(settingsView.getContext()));
        isMusicPlaying = false;
        settingsView.setButtonRadioDrawable(false);
        settingsView.getContext().stopService(musicIntent);
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
        settingsView.setTime(model.getDateAsString());
    }

    @Override
    public void onDestroy() {
        Intent musicIntent = new Intent(settingsView.getContext(), MusicService.class);
        settingsView.getContext().stopService(musicIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Music music = new Music(settingsView.getContext());
        switch (requestCode) {
            case Consts.REQUEST_CODE_FILE_CHOSER:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file g_path from the URI
                            String path = FileUtils.getPath(settingsView.getContext(), uri);
                            if (MusicFile.checkExtension(path)) {
                                music.setMusicFile(path);
                                model.setMusic(music);
                            } else {
                                settingsView.showToastNoMusicFile();
                                settingsView.setMusicButton(Music.MUSIC_TYPE.DEFAULT_RINGTONE.getCode());
                            }
                        } catch (Exception e) {
                            Log.v("File select error");
                        }
                    }
                } else
                    settingsView.setMusicButton(model.getMusicCode());
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

    @Override
    public void endAnimationAction(int actionID) {
        switch (actionID) {
            case R.id.btnSaveAlarm:
                ((Activity) settingsView.getContext()).finish();
                break;
            case R.id.rbFile:
                showFileBrowser();
                break;
            case R.id.rbRadio:
                if (isMusicPlaying)
                    stopPlayingRadio();
                else
                    startPlayingRadio();
                break;
            case R.id.rbRingtones:
                showRingtones();
                break;
        }
    }

    private void showFileBrowser() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, settingsView.getContext().getString(R.string.app_name));
        try {
            ((Activity) settingsView.getContext()).startActivityForResult(intent, Consts.REQUEST_CODE_FILE_CHOSER);
        } catch (ActivityNotFoundException | ClassCastException e) {
            showFileBrowserError();
            Log.v(e.getMessage());
        }
    }

    private void showRingtones() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, settingsView.getContext().getString(R.string.select_tone));
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(settingsView.getContext().getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
        try {
            ((Activity) settingsView.getContext()).startActivityForResult(intent, Consts.REQUEST_CODE_DEFAULT_RINGTONE);
        } catch (ClassCastException e) {
            showDefaultRingtonError();
            Log.v(e.getMessage());
        }
    }

    private void showDefaultRingtonError() {
        Toast.makeText(settingsView.getContext(), settingsView.getContext().getString(R.string.open_default_ringtones_error), Toast.LENGTH_SHORT).show();
    }

    private void showFileBrowserError() {
        Toast.makeText(settingsView.getContext(), settingsView.getContext().getString(R.string.open_file_browser_error), Toast.LENGTH_SHORT).show();
    }
}