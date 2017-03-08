package com.wanjian.sak.demo;

import android.app.Application;

import com.wanjian.sak.SAK;

/**
 * Created by wanjian on 2017/3/8.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SAK.init(this);
    }
}
