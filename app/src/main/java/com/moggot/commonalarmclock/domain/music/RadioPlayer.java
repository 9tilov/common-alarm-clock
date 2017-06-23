package com.moggot.commonalarmclock.domain.music;

import android.media.AudioManager;

import java.io.IOException;

public class RadioPlayer extends MusicPlayer {

    @Override
    public MusicPlayer initMediaPlayer(Music music) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(music.getMusicURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        return this;
    }
}
