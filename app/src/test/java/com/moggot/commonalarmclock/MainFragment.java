package com.moggot.commonalarmclock;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class MainFragment {

    @Test
    public void validateFragment() {
        MainFragment fragment = new MainFragment();
//        startFragment(fragment);
        assertNotNull("Fragment doesn't exist", fragment);
    }
}
