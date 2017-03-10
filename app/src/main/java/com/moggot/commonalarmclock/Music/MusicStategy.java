package com.moggot.commonalarmclock.Music;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by toor on 28.02.17.
 */

public interface MusicStategy {

    MediaPlayer init(Context ctx, String path);
}
