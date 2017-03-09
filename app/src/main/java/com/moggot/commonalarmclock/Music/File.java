package com.moggot.commonalarmclock.Music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by toor on 28.02.17.
 */

public class File implements MusicStategy {

    public void play(Context ctx, String path) {
        try {
            Uri uri = Uri.fromFile((new java.io.File(path)));
            MediaPlayer mediaPlayer = MediaPlayer.create(ctx, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }
}
