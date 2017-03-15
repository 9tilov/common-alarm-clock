package com.moggot.commonalarmclock.animation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.R;

/**
 * Created by toor on 15.03.17.
 */

public class MusicFileAnimationBounce extends AnimationBounce {

    public MusicFileAnimationBounce(Context context) {
        super(context);
    }

    @Override
    protected void startAnimation() {
        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation arg0) {
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation arg0) {
                showChooser();
            }
        });
    }

    private void showChooser() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, context.getString(R.string.app_name));
        try {
            ((Activity)context).startActivityForResult(intent, Consts.REQUEST_CODE_FILE_CHOSER);
        } catch (ActivityNotFoundException e) {
        }
    }

}
