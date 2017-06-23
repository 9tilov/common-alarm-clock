package com.moggot.commonalarmclock.domain.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.moggot.commonalarmclock.Consts;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MusicPlayer musicPlayer = null;
    private boolean isServiceStopped = false;

    public int onStartCommand(Intent intent, int flags, int startID) {

        Music music = intent.getParcelableExtra(Consts.EXTRA_MUSIC);

        musicPlayer = PlayerFactory.create(music);
        musicPlayer.setListener(this);

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
        if (musicPlayer.isPlaying())
            musicPlayer.stop();
    }
}