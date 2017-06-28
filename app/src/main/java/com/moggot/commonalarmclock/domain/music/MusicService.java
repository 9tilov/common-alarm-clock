package com.moggot.commonalarmclock.domain.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.presentation.di.App;
import com.moggot.commonalarmclock.presentation.di.modules.AlarmModule;

import javax.inject.Inject;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private boolean isServiceStopped = false;

    @Inject
    MusicPlayer musicPlayer;

    public int onStartCommand(Intent intent, int flags, int startID) {

        App.getInstance().getAppComponent().plus(new AlarmModule()).inject(this);

        Music music = intent.getParcelableExtra(Consts.EXTRA_MUSIC);
        musicPlayer.create(music, this);

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