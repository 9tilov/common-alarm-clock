package com.moggot.commonalarmclock.domain.music;

import android.media.RingtoneManager;

import java.io.IOException;

public class RingtonePlayer extends MusicPlayer {

    @Override
    public MusicPlayer initMediaPlayer(Music music) {
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
