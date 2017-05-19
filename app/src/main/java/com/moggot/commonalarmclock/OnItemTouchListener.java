package com.moggot.commonalarmclock;

import android.view.View;

/**
 * Created by toor on 18.05.17.
 */

public interface OnItemTouchListener {

    void onCardViewTap(View view, int position);

    void onStateChange(View view, int position);

}
