package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by toor on 10.03.17.
 */

public class MusicPlayer {

    private MusicStategy musicStategy;

    public MusicPlayer(MusicStategy musicStategy) {
        this.musicStategy = musicStategy;
    }

    public void setStrategy(MusicStategy musicStategy) {
        this.musicStategy = musicStategy;
    }

    public MediaPlayer init(Context context, String path) {
        return musicStategy.init(context, path);
    }

}
