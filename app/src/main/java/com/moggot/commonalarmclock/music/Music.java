package com.moggot.commonalarmclock.music;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.moggot.commonalarmclock.Consts;

public class Music implements Parcelable {

    private Context context;
    private Consts.MUSIC_TYPE musicType;
    private String musicURL;

    public Music(Context context) {
        this.context = context;

        setInternetRadio();
    }

    public void setInternetRadio() {
        this.musicType = Consts.MUSIC_TYPE.RADIO;
        this.musicURL = Consts.RADIO_URL;

        setMusicDependOnInternetConnection();
    }

    public void setDefaultRingtone(String url) {
        this.musicType = Consts.MUSIC_TYPE.DEFAULT_RINGTONE;
        this.musicURL = url;
    }

    public void setMusicFile(String url) {
        this.musicType = Consts.MUSIC_TYPE.MUSIC_FILE;
        this.musicURL = url;
    }

    private void setMusicDependOnInternetConnection() {
        if (musicType == Consts.MUSIC_TYPE.RADIO) {
            if (!isNetworkAvailable()) {
                musicType = Consts.MUSIC_TYPE.DEFAULT_RINGTONE;
                musicURL = Consts.DEFAULT_RINGTONE_URL;
            } else
                musicURL = Consts.RADIO_URL;
        }
    }

    public Consts.MUSIC_TYPE getMusicType() {
        return musicType;
    }

    public String getMusicURL() {
        return musicURL;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
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
        this.musicType = Consts.MUSIC_TYPE.fromInteger(parcel.readInt());
        this.musicURL = parcel.readString();
    }
}
