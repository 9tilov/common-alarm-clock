package com.moggot.commonalarmclock.domain.music;

import android.media.AudioManager;

import java.io.IOException;

public class FilePlayer extends MusicPlayer {

    @Override
    public MusicPlayer initMediaPlayer(Music music) {
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
