package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

public class PlayerManager {

    private Context context;

    public PlayerManager(Context context) {
        this.context = context;
    }

    public MediaPlayer createPlayer(Music music) {
        PlayerCreator player;
        switch (music.getMusicType()) {
            case RADIO:
                if (isNetworkAvailable())
                    player = new RadioPlayer(context);
                else {
                    internetUnavailable();
                    player = new RingtonePlayer(context);
                    music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                }
                break;
            case DEFAULT_RINGTONE:
                player = new RingtonePlayer(context);
                break;
            case MUSIC_FILE:
                player = new FilePlayer(context);
                break;
            default:
                if (isNetworkAvailable())
                    player = new RadioPlayer(context);
                else {
                    internetUnavailable();
                    player = new RingtonePlayer(context);
                    music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                }
                break;
        }

        return player.create(music);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void internetUnavailable() {
        Toast.makeText(context,
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }
}
