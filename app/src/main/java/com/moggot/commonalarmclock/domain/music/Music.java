package com.moggot.commonalarmclock.domain.music;

import android.media.RingtoneManager;
import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable {

    public static final String RADIO_URL = "http://pulseedm.cdnstream1.com:8124/1373_128";
    public static final String DEFAULT_RINGTONE_URL = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();

    public enum MUSIC_TYPE {
        MUSIC_FILE(0),
        RADIO(1),
        RINGTONE(2);

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
                    return RINGTONE;
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

    public Music(MUSIC_TYPE type, String path) {
        this.musicType = type;
        this.musicURL = path;
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
