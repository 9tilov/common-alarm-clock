package com.moggot.commonalarmclock;

import android.media.RingtoneManager;

public interface Consts {

    int TOMORROW = 0;

    String EXTRA_ID = "_id";
    String EXTRA_MUSIC = "music";

    String FIREBASE_ITEM_ID = "ID";
    String FIREBASE_ITEM_NAME = "NAME";
    String FIREBASE_CONTENT_TYPE = "IMAGE";

    int REQUEST_CODE_DEFAULT_RINGTONE = 1;
    int REQUEST_CODE_FILE_CHOSER = 2;
    int REQUEST_CODE_ACTIVITY_SETTINGS = 3;

    int SNOOZE_TIME_IN_MINUTES = 5;

    int NO_ID = 0;

    String RADIO_URL = "http://pulseedm.cdnstream1.com:8124/1373_128";
    String DEFAULT_RINGTONE_URL = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
}
