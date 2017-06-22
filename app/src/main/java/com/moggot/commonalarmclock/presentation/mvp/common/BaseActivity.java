package com.moggot.commonalarmclock.presentation.mvp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moggot.commonalarmclock.presentation.di.App;
import com.moggot.commonalarmclock.presentation.di.component.AppComponent;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(App.getInstance().getAppComponent());
    }

    protected abstract void injectDependencies(AppComponent appComponent);
}
