package com.moggot.commonalarmclock.mvp.main;

import com.moggot.commonalarmclock.ItemTouchHelperAdapter;

public interface MainPresenter extends ItemTouchHelperAdapter {

    void setMainModel(MainModel mainModel);

    void onActivityResult();
}
