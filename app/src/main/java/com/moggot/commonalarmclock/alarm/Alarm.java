package com.moggot.commonalarmclock.alarm;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here

import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.commonalarmclock.Consts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;
import java.util.concurrent.CancellationException;
// KEEP INCLUDES END

/**
 * Entity mapped to table "ALARM".
 */
@Entity
public class Alarm {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private java.util.Date date;

    @NotNull
    private String requestCodes;
    private Boolean isSnoozeEnable;
    private Boolean isMathEnable;
    private String name;

    @NotNull
    private String musicPath;
    private int musicType;
    private boolean state;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Alarm() {
    }

    public Alarm(Long id) {
        this.id = id;
    }

    @Generated
    public Alarm(Long id, java.util.Date date, String requestCodes, Boolean isSnoozeEnable, Boolean isMathEnable, String name, String musicPath, int musicType, boolean state) {
        this.id = id;
        this.date = date;
        this.requestCodes = requestCodes;
        this.isSnoozeEnable = isSnoozeEnable;
        this.isMathEnable = isMathEnable;
        this.name = name;
        this.musicPath = musicPath;
        this.musicType = musicType;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public java.util.Date getDate() {
        return date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate(@NotNull java.util.Date date) {
        this.date = date;
    }

    @NotNull
    public String getRequestCodes() {
        return requestCodes;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRequestCodes(@NotNull String requestCodes) {
        this.requestCodes = requestCodes;
    }

    public Boolean getIsSnoozeEnable() {
        return isSnoozeEnable;
    }

    public void setIsSnoozeEnable(Boolean isSnoozeEnable) {
        this.isSnoozeEnable = isSnoozeEnable;
    }

    public Boolean getIsMathEnable() {
        return isMathEnable;
    }

    public void setIsMathEnable(Boolean isMathEnable) {
        this.isMathEnable = isMathEnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getMusicPath() {
        return musicPath;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMusicPath(@NotNull String musicPath) {
        this.musicPath = musicPath;
    }

    public int getMusicType() {
        return musicType;
    }

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    // KEEP METHODS - put your custom methods here

    public void setMusic(Integer musicType, String musicPath) {
        this.musicType = musicType;
        this.musicPath = musicPath;
    }

    public SparseIntArray getIDs() {
        Type type = new TypeToken<SparseIntArray>() {
        }.getType();
        return new Gson().fromJson(getRequestCodes(), type);
    }

    public void setIDs(SparseIntArray ids) {
        this.requestCodes = new Gson().toJson(ids);
    }

    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public long getTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public Consts.MUSIC_TYPE getMusicTypeEnum() {
        return Consts.MUSIC_TYPE.fromInteger(musicType);
    }
    // KEEP METHODS END

}
