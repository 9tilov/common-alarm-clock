package com.moggot.commonalarmclock.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.moggot.commonalarmclock.Consts;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer = null;
    private boolean isServiceStopped = false;

    public int onStartCommand(Intent intent, int flags, int startID) {

        Music music = intent.getParcelableExtra(Consts.EXTRA_MUSIC);

        mediaPlayer = MusicPlayer.createPlayer(music);
        mediaPlayer.setOnPreparedListener(this);

        return super.onStartCommand(intent, flags, startID);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        if (!isServiceStopped)
            mediaPlayer.start();
    }

    public void onDestroy() {
        super.onDestroy();
        isServiceStopped = true;
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }
}