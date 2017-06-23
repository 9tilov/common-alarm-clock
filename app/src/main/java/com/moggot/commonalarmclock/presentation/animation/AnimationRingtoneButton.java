package com.moggot.commonalarmclock.presentation.animation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.domain.utils.Log;
import com.moggot.commonalarmclock.R;

public class AnimationRingtoneButton extends AnimationBounce {

    public AnimationRingtoneButton(Context context) {
        super(context);
    }

    @Override
    protected void animationAction() {
        showRingtones();
    }

    private void showRingtones() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, context.getString(R.string.select_tone));
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(context.getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
        try {
            ((Activity) context).startActivityForResult(intent, Consts.REQUEST_CODE_DEFAULT_RINGTONE);
        } catch (ClassCastException e) {
            showDefaultRingtonError();
            Log.v(e.getMessage());
        }
    }

    private void showDefaultRingtonError() {
        Toast.makeText(context, context.getString(R.string.open_default_ringtones_error), Toast.LENGTH_SHORT).show();
    }
}
