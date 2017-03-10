package com.moggot.commonalarmclock.Music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by toor on 28.02.17.
 */

public class Radio implements MusicStategy {

    public MediaPlayer init(Context ctx, String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        return mediaPlayer;
    }
}
