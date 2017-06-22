package com.moggot.commonalarmclock.mvp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moggot.commonalarmclock.presentation.App;
import com.moggot.commonalarmclock.presentation.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(App.getInstance().getAppComponent());
    }

    protected abstract void injectDependencies(AppComponent appComponent);
}
