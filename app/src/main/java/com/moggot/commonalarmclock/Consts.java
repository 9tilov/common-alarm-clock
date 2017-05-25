package com.moggot.commonalarmclock;

import android.media.RingtoneManager;

/**
 * Created by dmitry on 25.11.16.
 */

public final class Consts {

    public static final int TOMORROW = 0;

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
            }
            return null;
        }

        public int getCode() {
            return this.code;
        }
    }

    public final static String EXTRA_ID = "_id";
    public final static String EXTRA_POSITION = "position";
    public final static String EXTRA_TYPE = "code";
    public final static String EXTRA_PATH = "path";

    public final static String FIREBASE_ITEM_ID = "ID";
    public final static String FIREBASE_ITEM_NAME = "NAME";
    public final static String FIREBASE_CONTENT_TYPE = "IMAGE";

    public final static int REQUEST_CODE_DEFAULT_RINGTONE = 1;
    public final static int REQUEST_CODE_FILE_CHOSER = 2;
    public final static int REQUEST_CODE_ACTIVITY_SETTINGS = 3;

    public final static int SNOOZE_TIME_IN_MINUTES = 5;

    public static final int NO_POSITION = -1;

    public static final int NO_ID = 0;

    public final static String DATA_RADIO = "http://pulseedm.cdnstream1.com:8124/1373_128";
    public final static String DATA_DEFAULT_RINGTONE = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();

}
