package com.moggot.commonalarmclock.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moggot.commonalarmclock.mvp.BaseView;
import com.moggot.commonalarmclock.presentation.App;
import com.moggot.commonalarmclock.presentation.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(App.getInstance().getAppComponent());
    }

    protected abstract void injectDependencies(AppComponent appComponent);

    @Override
    public Context getContext() {
        return this;
    }
}
