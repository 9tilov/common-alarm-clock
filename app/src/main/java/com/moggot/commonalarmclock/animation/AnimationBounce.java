package com.moggot.commonalarmclock.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.moggot.commonalarmclock.R;

public class AnimationBounce {

    protected View view;
    protected final Animation animation;

    public AnimationBounce(View view) {
        this.view = view;
        this.animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);

        initAnimation();
    }

    private void initAnimation() {
        double animationDuration = 0.5 * 1000;
        animation.setDuration((long) animationDuration);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.50, 15);
        animation.setInterpolator(interpolator);
    }

    public final void animate(int actionID, CallbackAnimation callbackAnimation) {
        initButton(view);
        startAnimation(actionID, callbackAnimation);
    }

    private void initButton(View view) {
        view.startAnimation(animation);
    }

    private void startAnimation(final int actionID, final CallbackAnimation callbackAnimation) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                callbackAnimation.actionOfAnimationEnd(actionID);
            }
        });
    }
}
