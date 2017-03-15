package com.moggot.commonalarmclock.animation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.ActivitySettings;
import com.moggot.commonalarmclock.Consts;

/**
 * Created by toor on 15.03.17.
 */

public class AddAlarmAnimation extends Animation {

    public AddAlarmAnimation(Context context) {
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
                Intent intent = new Intent(context, ActivitySettings.class);
                ((Activity) context).startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
            }
        });
    }
}
