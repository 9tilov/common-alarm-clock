package com.moggot.commonalarmclock.domain.music;

import android.media.MediaPlayer;

public abstract class MusicPlayer {

    protected MediaPlayer mediaPlayer;

    public MusicPlayer() {
        this.mediaPlayer = new MediaPlayer();
    }

    public void setListener(MediaPlayer.OnPreparedListener listener) {
        mediaPlayer.setOnPreparedListener(listener);
    }

    public void start() {
        mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public abstract MusicPlayer initMediaPlayer(Music music);
}
