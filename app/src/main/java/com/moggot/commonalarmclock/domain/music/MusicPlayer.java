package com.moggot.commonalarmclock.domain.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

import java.io.IOException;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;

    public void init(Music music, MediaPlayer.OnPreparedListener listener) {
        mediaPlayer = new MediaPlayer();
        switch (music.getMusicType()) {
            case RADIO:
                initRadio(music);
                break;
            case RINGTONE:
                initRingtone(music);
                break;
            case MUSIC_FILE:
                initMusicFile(music);
                break;
            default:
                initRadio(music);
                break;
        }
        mediaPlayer.setOnPreparedListener(listener);
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initRadio(Music music) {
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepareAsync();
    }

    private void initRingtone(Music music) {
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMusicFile(Music music) {
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (!isPlaying())
            mediaPlayer.start();
    }

    public void stop() {
        if (isPlaying())
            mediaPlayer.stop();
        releaseMP();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}
