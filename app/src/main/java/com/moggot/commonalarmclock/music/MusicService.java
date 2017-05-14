package com.moggot.commonalarmclock.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer = null;

    private final static String LOG_TAG = MusicService.class.getSimpleName();

    private boolean isServiceStopped = false;

    public int onStartCommand(Intent intent, int flags, int startID) {

        Consts.MUSIC_TYPE type = Consts.MUSIC_TYPE.fromInteger(intent.getIntExtra(Consts.EXTRA_TYPE, Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType()));
        if (type == null)
            throw new NullPointerException("type is null");
        String path = intent.getStringExtra(Consts.EXTRA_PATH);

        Player player;

        switch (type) {
            case RADIO:
                if (isNetworkAvailable())
                    player = new Player(new Radio());
                else {
                    internetUnavailable();
                    player = new Player(new Ringtone());
                    path = Consts.DATA_DEFAULT_RINGTONE;
                }
                break;
            case DEFAULT_RINGTONE:
                player = new Player(new Ringtone());
                break;
            case MUSIC_FILE:
                player = new Player(new File());
                break;
            default:
                if (isNetworkAvailable())
                    player = new Player(new Radio());
                else {
                    internetUnavailable();
                    player = new Player(new Ringtone());
                    path = Consts.DATA_DEFAULT_RINGTONE;
                }
                break;
        }

        mediaPlayer = player.init(this, path);
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void internetUnavailable() {
        Toast.makeText(this,
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }
}
