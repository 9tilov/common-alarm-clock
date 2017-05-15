package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class FilePlayer extends PlayerCreator {

    private Context context;

    public FilePlayer(Context context) {
        this.context = context;
    }

    public MediaPlayer create(Music music) {
        MediaPlayer mediaPlayer = null;
        try {
            Uri uri = Uri.fromFile((new java.io.File(music.getMusicPath())));
            mediaPlayer = MediaPlayer.create(context, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return mediaPlayer;
    }
}
