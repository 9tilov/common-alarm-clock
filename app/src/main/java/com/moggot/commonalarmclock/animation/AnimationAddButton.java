package com.moggot.commonalarmclock.animation;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.domain.utils.ActivityUtils;
import com.moggot.commonalarmclock.presentation.mvp.view.fragments.SettingsFragment;

import static com.moggot.commonalarmclock.Consts.NO_ID;

public class AnimationAddButton extends AnimationBounce {

    public AnimationAddButton(Context context) {
        super(context);
    }

    @Override
    protected void animationAction() {
        SettingsFragment settingsFragment = SettingsFragment.newInstance(NO_ID);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        ActivityUtils.replaceFragmentInActivity(fragmentManager, settingsFragment, R.id.root_frame);
    }
}
