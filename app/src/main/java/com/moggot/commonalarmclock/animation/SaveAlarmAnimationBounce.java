package com.moggot.commonalarmclock.animation;

import android.app.Activity;
import android.content.Context;

public class SaveAlarmAnimationBounce extends AnimationBounce {

    public SaveAlarmAnimationBounce(Context context) {
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
