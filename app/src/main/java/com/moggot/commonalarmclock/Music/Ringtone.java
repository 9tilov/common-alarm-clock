package com.moggot.commonalarmclock.Music;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by toor on 28.02.17.
 */

public class Ringtone implements MusicStategy {

    public void play(Context ctx, String path) {
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Uri alarm = Uri.parse(path);
            MediaPlayer mediaPlayer = MediaPlayer.create(ctx, alarm);

            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }
}
