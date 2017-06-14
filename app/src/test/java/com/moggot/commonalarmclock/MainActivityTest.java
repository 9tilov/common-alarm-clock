package com.moggot.commonalarmclock;

import android.os.Build;
import android.widget.FrameLayout;

import com.moggot.commonalarmclock.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .get();
    }

    @Test
    public void validateFragmentContainer() {
        FrameLayout root = (FrameLayout) activity.findViewById(R.id.root_frame);
        assertNotNull("Container not found", root);
    }

}
