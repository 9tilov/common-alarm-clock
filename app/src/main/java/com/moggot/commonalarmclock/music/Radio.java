package com.moggot.commonalarmclock.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class Radio extends Creator {

    public MediaPlayer create(Music music) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        return mediaPlayer;
    }
}
