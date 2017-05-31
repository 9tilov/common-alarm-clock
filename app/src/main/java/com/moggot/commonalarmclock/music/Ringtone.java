package com.moggot.commonalarmclock.music;

import android.media.MediaPlayer;
import android.media.RingtoneManager;

import java.io.IOException;

public class Ringtone extends Creator {

    public MediaPlayer create(Music music) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
