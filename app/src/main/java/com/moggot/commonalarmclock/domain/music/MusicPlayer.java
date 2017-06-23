package com.moggot.commonalarmclock.domain.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import static com.moggot.commonalarmclock.Consts.EXTRA_MUSIC;
import static com.moggot.commonalarmclock.domain.music.Music.MUSIC_TYPE.RADIO;
import static com.moggot.commonalarmclock.domain.music.Music.RADIO_URL;

public class MusicPlayer {

    private boolean isPlaying = false;
    private Context context;

    public MusicPlayer(Context context) {
        this.context = context;
    }

    public static MediaPlayer createPlayer(Music music) {
        Creator player;
        switch (music.getMusicType()) {
            case RADIO:
                player = new Radio();
                break;
            case DEFAULT_RINGTONE:
                player = new Ringtone();
                break;
            case MUSIC_FILE:
                player = new File();
                break;
            default:
                player = new Ringtone();
                break;
        }
        return player.create(music);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void startPlayingRadio() {
        Intent musicIntent = new Intent(context, MusicService.class);
        musicIntent.putExtra(EXTRA_MUSIC, new Music(RADIO, RADIO_URL));
        isPlaying = true;
        context.startService(musicIntent);
    }

    public void stopPlayingRadio() {
        Intent musicIntent = new Intent(context, MusicService.class);
        musicIntent.putExtra(EXTRA_MUSIC, new Music(RADIO, RADIO_URL));
        isPlaying = false;
        context.stopService(musicIntent);
    }
}
