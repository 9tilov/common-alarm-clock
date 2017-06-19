package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.domain.utils.NetworkConnectionChecker;

public class Music implements Parcelable {

    public enum MUSIC_TYPE {
        MUSIC_FILE(0),
        RADIO(1),
        DEFAULT_RINGTONE(2);

        private final int code;

        MUSIC_TYPE(int code) {
            this.code = code;
        }

        public static MUSIC_TYPE fromInteger(int x) {
            switch (x) {
                case 0:
                    return MUSIC_FILE;
                case 1:
                    return RADIO;
                case 2:
                    return DEFAULT_RINGTONE;
                default:
                    return RADIO;
            }
        }

        public int getCode() {
            return this.code;
        }
    }

    private MUSIC_TYPE musicType;
    private String musicURL;
    private Context context;

    public Music(Context context) {
        this.context = context;

        setInternetRadio();
    }

    public void setMusic(MUSIC_TYPE type, String musicURL) {
        this.musicType = type;
        this.musicURL = musicURL;
    }

    public void setInternetRadio() {
        this.musicType = MUSIC_TYPE.RADIO;
        this.musicURL = Consts.RADIO_URL;

        setMusicDependOnInternetConnection();
    }

    public void setDefaultRingtone(String url) {
        this.musicType = MUSIC_TYPE.DEFAULT_RINGTONE;
        this.musicURL = url;
    }

    public void setMusicFile(String url) {
        this.musicType = MUSIC_TYPE.MUSIC_FILE;
        this.musicURL = url;
    }

    private void setMusicDependOnInternetConnection() {
        if (musicType == MUSIC_TYPE.RADIO) {
            NetworkConnectionChecker connectionChecker = new NetworkConnectionChecker(context);
            if (!connectionChecker.isNetworkAvailable()) {
                musicType = MUSIC_TYPE.DEFAULT_RINGTONE;
                musicURL = Consts.DEFAULT_RINGTONE_URL;
            } else
                musicURL = Consts.RADIO_URL;
        }
    }

    public MUSIC_TYPE getMusicType() {
        return musicType;
    }

    public String getMusicURL() {
        return musicURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(musicType.getCode());
        dest.writeString(musicURL);
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        // распаковываем объект из Parcel
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private Music(Parcel parcel) {
        this.musicType = MUSIC_TYPE.fromInteger(parcel.readInt());
        this.musicURL = parcel.readString();
    }
}
