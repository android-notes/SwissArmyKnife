package com.wanjian.sak.demo;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;

//import com.squareup.leakcanary.LeakCanary;

//import leakcanary.LeakCanary;
import me.weishu.reflection.Reflection;

/**
 * Created by wanjian on 2018/2/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        me.weishu.reflection.Reflection.unseal(this);
    }
}
