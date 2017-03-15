package com.moggot.commonalarmclock.animation;

import android.app.Activity;
import android.content.Context;

/**
 * Created by toor on 15.03.17.
 */

public class SaveAlarmAnimation extends Animation {

    public SaveAlarmAnimation(Context context) {
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
                ((Activity) context).finish();
            }
        });
    }
}