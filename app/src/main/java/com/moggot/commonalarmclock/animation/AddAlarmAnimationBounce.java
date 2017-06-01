package com.moggot.commonalarmclock.animation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.moggot.commonalarmclock.activity.ActivitySettings;
import com.moggot.commonalarmclock.Consts;

public class AddAlarmAnimationBounce extends AnimationBounce {

    public AddAlarmAnimationBounce(Context context) {
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
