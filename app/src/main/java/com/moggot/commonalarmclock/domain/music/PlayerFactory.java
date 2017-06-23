package com.moggot.commonalarmclock.domain.music;

public class PlayerFactory {

    public static MusicPlayer create(Music music) {
        MusicPlayer player;
        switch (music.getMusicType()) {
            case RADIO:
                player = new RadioPlayer();
                break;
            case RINGTONE:
                player = new RingtonePlayer();
                break;
            case MUSIC_FILE:
                player = new FilePlayer();
                break;
            default:
                player = new RadioPlayer();
                break;
        }
        return player.initMediaPlayer(music);
    }
}
