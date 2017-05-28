package com.moggot.commonalarmclock;

import android.media.RingtoneManager;

public final class Consts {

    public static final int TOMORROW = 0;



    public final static String EXTRA_ID = "_id";
    public final static String EXTRA_MUSIC = "music";

    public final static String FIREBASE_ITEM_ID = "ID";
    public final static String FIREBASE_ITEM_NAME = "NAME";
    public final static String FIREBASE_CONTENT_TYPE = "IMAGE";

    public final static int REQUEST_CODE_DEFAULT_RINGTONE = 1;
    public final static int REQUEST_CODE_FILE_CHOSER = 2;
    public final static int REQUEST_CODE_ACTIVITY_SETTINGS = 3;

    public final static int SNOOZE_TIME_IN_MINUTES = 5;

    public static final int NO_ID = 0;

    public final static String RADIO_URL = "http://pulseedm.cdnstream1.com:8124/1373_128";
    public final static String DEFAULT_RINGTONE_URL = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();

}
