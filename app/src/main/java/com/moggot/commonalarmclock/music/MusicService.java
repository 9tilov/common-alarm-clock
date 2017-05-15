package com.moggot.commonalarmclock.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.moggot.commonalarmclock.Consts;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private final static String LOG_TAG = MusicService.class.getSimpleName();

    private MediaPlayer mediaPlayer = null;
    private boolean isServiceStopped = false;

    public int onStartCommand(Intent intent, int flags, int startID) {

        Consts.MUSIC_TYPE type = Consts.MUSIC_TYPE.fromInteger(intent.getIntExtra(Consts.EXTRA_TYPE, Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType()));
        if (type == null)
            throw new NullPointerException("type is null");
        String path = intent.getStringExtra(Consts.EXTRA_PATH);

        Music music = new Music(type, path);
        PlayerManager playerManager = new PlayerManager(this);

        mediaPlayer = playerManager.createPlayer(music);
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