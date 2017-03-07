package com.wanjian.sak;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.wanjian.sak.config.Config;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {


    private static Manager sManager;

    private SAK() {
    }

    public static synchronized void init(Application application) {
        Check.isNull(application, "application can not be null !");
        init(application, null);
    }

    public static synchronized void init(Application application, Config config) {
        Check.isNull(application, "application can not be null !");
        if (sManager == null) {
            if (config == null) {
                config = new Config.Build(application).build();
            }
            sManager = new Manager(application, config);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                application.registerActivityLifecycleCallbacks(new LifecycleCallbacksAdapter() {
                    @Override
                    public void onActivityResumed(Activity activity) {
                        super.onActivityResumed(activity);
                        attach(activity);
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        super.onActivityPaused(activity);
                        detch(activity);
                    }
                });
            }
        }

    }


    public static void attach(Activity activity) {
        Check.isNull(sManager, "init first!");
        Check.isNull(activity, "activity can not be null !");
        sManager.attach(activity);
    }

    public static void detch(Activity activity) {
        Check.isNull(sManager, "init first!");
        Check.isNull(activity, "activity can not be null !");
        sManager.detach(activity);
    }

    public static void unInstall(Activity activity) {
        Check.isNull(activity, "activity can not be null !");
        detch(activity);
    }

}
