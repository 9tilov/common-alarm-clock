package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

public class RingtonePlayer extends PlayerCreator {

    private Context context;

    public RingtonePlayer(Context context) {
        this.context = context;
    }

    public MediaPlayer create(Music music) {
        MediaPlayer mediaPlayer = null;
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Uri alarm = Uri.parse(music.getMusicPath());
            mediaPlayer = MediaPlayer.create(context, alarm);

            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
