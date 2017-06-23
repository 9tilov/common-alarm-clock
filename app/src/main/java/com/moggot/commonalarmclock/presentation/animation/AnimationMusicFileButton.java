package com.moggot.commonalarmclock.presentation.animation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.moggot.commonalarmclock.Consts;
import com.moggot.commonalarmclock.domain.utils.Log;
import com.moggot.commonalarmclock.R;

public class AnimationMusicFileButton extends AnimationBounce {

    public AnimationMusicFileButton(Context context) {
        super(context);
    }

    @Override
    protected void animationAction() {
        showFileBrowser();
    }

    private void showFileBrowser() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, context.getString(R.string.app_name));
        try {
            ((Activity) context).startActivityForResult(intent, Consts.REQUEST_CODE_FILE_CHOSER);
        } catch (ActivityNotFoundException | ClassCastException e) {
            showFileBrowserError();
            Log.v(e.getMessage());
        }
    }

    private void showFileBrowserError() {
        Toast.makeText(context, context.getString(R.string.open_file_browser_error), Toast.LENGTH_SHORT).show();
    }
}
