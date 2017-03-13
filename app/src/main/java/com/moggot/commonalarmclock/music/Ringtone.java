package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by toor on 28.02.17.
 */

public class Ringtone implements MusicStategy {

    public MediaPlayer init(Context context, String path) {
        MediaPlayer mediaPlayer = null;
        try {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Uri alarm = Uri.parse(path);
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
