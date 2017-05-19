package com.moggot.commonalarmclock;

/**
 * Created by javierg on 12/10/2016.
 */

public interface ItemTouchHelperAdapter {
    /**
     * Called when an item has been dismissed by a swipe.<br/>
     */
    void onItemDismiss(int position);

    void onItemShow(int position);
}
