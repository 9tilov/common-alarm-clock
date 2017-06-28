package com.moggot.commonalarmclock.domain.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

import java.io.IOException;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;

    public void create(Music music, MediaPlayer.OnPreparedListener listener) {
        mediaPlayer = new MediaPlayer();
        switch (music.getMusicType()) {
            case RADIO:
                createRadioPlayer(music);
                break;
            case RINGTONE:
                createRingtonePlayer(music);
                break;
            case MUSIC_FILE:
                createMusicFilePlayer(music);
                break;
            default:
                createRadioPlayer(music);
                break;
        }
        mediaPlayer.setOnPreparedListener(listener);
    }

    private void createRadioPlayer(Music music) {
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepareAsync();
    }

    private void createRingtonePlayer(Music music) {
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMusicFilePlayer(Music music) {
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

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
        releaseMP();
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

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}
