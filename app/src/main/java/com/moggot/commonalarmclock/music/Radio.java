package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by toor on 15.05.17.
 */

public class Radio extends Creator {

    private Context context;

    public Radio(Context context) {
        this.context = context;
    }

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
