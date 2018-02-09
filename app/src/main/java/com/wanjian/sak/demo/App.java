package com.wanjian.sak.demo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wanjian on 2018/2/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
