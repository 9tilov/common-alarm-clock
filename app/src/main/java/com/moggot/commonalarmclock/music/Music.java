package com.moggot.commonalarmclock.music;

import com.moggot.commonalarmclock.Consts;

public class Music {

    private Consts.MUSIC_TYPE type;
    private String musicPath;

    public Music(Consts.MUSIC_TYPE type, String musicPath) {
        this.type = type;
        this.musicPath = musicPath;
    }

    public void setType(Consts.MUSIC_TYPE type) {
        this.type = type;
    }

    public void setPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public Consts.MUSIC_TYPE getType() {
        return type;
    }

    public String getMusicPath() {
        return musicPath;
    }
}
