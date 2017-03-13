package com.moggot.commonalarmclock.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

/**
 * Created by toor on 10.03.17.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer = null;

    private final static String LOG_TAG = "MusicService";

    private boolean isServiceStopped = false;

    public int onStartCommand(Intent intent, int flags, int startID) {
        Consts.MUSIC_TYPE type = Consts.MUSIC_TYPE.fromInteger(intent.getIntExtra(Consts.EXTRA_TYPE, Consts.MUSIC_TYPE.DEFAULT_RINGTONE.getType()));
        String path = intent.getStringExtra(Consts.EXTRA_PATH);

        MusicPlayer musicPlayer = null;

        switch (type) {
            case RADIO:
                if (isNetworkAvailable())
                    musicPlayer = new MusicPlayer(new Radio());
                else {
                    internetUnavailable();
                    musicPlayer = new MusicPlayer(new Ringtone());
                    path = Consts.DATA_DEFAULT_RINGTONE;
                }
                break;
            case DEFAULT_RINGTONE:
                musicPlayer = new MusicPlayer(new Ringtone());
                break;
            case MUSIC_FILE:
                musicPlayer = new MusicPlayer(new File());
                break;
            default:
                if (isNetworkAvailable())
                    musicPlayer = new MusicPlayer(new Radio());
                else {
                    internetUnavailable();
                    musicPlayer = new MusicPlayer(new Ringtone());
                    path = Consts.DATA_DEFAULT_RINGTONE;
                }
                break;

        }

        mediaPlayer = musicPlayer.init(this, path);
        mediaPlayer.setOnPreparedListener(this);

        return super.onStartCommand(intent, flags, startID);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        if (!isServiceStopped)
            mediaPlayer.start();
        Log.v(LOG_TAG, "start");
    }

    public void onDestroy() {
        super.onDestroy();
        isServiceStopped = true;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        Log.v(LOG_TAG, "stop");
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void internetUnavailable() {
        Toast.makeText(getApplicationContext(),
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }
}
