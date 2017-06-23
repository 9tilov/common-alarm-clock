package com.moggot.commonalarmclock.presentation.animation;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

public class AnimationSaveButton extends AnimationBounce {

    public AnimationSaveButton(Context context) {
        super(context);
    }

    @Override
    protected void animationAction() {
        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
    }
}
