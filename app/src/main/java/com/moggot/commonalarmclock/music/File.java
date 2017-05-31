package com.moggot.commonalarmclock.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class File extends Creator {

    public MediaPlayer create(Music music) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return mediaPlayer;
    }
}
