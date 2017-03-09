package com.moggot.commonalarmclock;

import android.media.RingtoneManager;

/**
 * Created by dmitry on 25.11.16.
 */

public final class Consts {

    public enum DAYS {
        TOMORROW((byte) 0b0000000),
        MONDAY((byte) 0b0000001),
        TUESDAY((byte) 0b0000010),
        WEDNESDAY((byte) 0b0000100),
        THURSDAY((byte) 0b0001000),
        FRIDAY((byte) 0b0010000),
        SATURDAY((byte) 0b0100000),
        SUNDAY((byte) 0b1000000);

        private final byte code;

        DAYS(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return this.code;
        }

    }

    public enum MUSIC_TYPE {
        MUSIC_FILE(0),
        RADIO(1),
        DEFAULT_RINGTONE(2);

        private final int type;

        MUSIC_TYPE(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    public final static String EXTRA_ID = "_id";

    public final static int REQUEST_CODE_DEFAULT_RINGTONE = 1;
    public final static int REQUEST_CODE_FILE_CHOSER = 2;
    public final static int REQUEST_CODE_ACTIVITY_SETTINGS = 3;

    public final static String DATA_RADIO = "http://online.radiorecord.ru:8101/rr_128";
    public final static String DATA_DEFAULT_RINGTONE = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();

}
