package com.wanjian.sak.demo;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wanjian on 2018/2/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        DisplayMetrics sys = Resources.getSystem().getDisplayMetrics();

        DisplayMetrics app = getResources().getDisplayMetrics();
        System.out.println(app);
    }
}
