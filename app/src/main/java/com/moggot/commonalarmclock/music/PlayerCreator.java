package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.NetworkConnectionChecker;

public class PlayerCreator {

    private Context context;

    public PlayerCreator(Context context) {
        this.context = context;
    }

    public MediaPlayer createPlayer(Music music) {
        Creator player;
        NetworkConnectionChecker connectionChecker = new NetworkConnectionChecker(context);
        switch (music.getMusicType()) {
            case RADIO:
                if (connectionChecker.isNetworkAvailable())
                    player = new Radio(context);
                else {
                    player = new Ringtone(context);
                    music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                }
                break;
            case DEFAULT_RINGTONE:
                player = new Ringtone(context);
                break;
            case MUSIC_FILE:
                player = new File(context);
                break;
            default:
                if (connectionChecker.isNetworkAvailable())
                    player = new Radio(context);
                else {
                    player = new Ringtone(context);
                    music.setDefaultRingtone(Consts.DEFAULT_RINGTONE_URL);
                }
                break;
        }
        return player.create(music);
    }
}
