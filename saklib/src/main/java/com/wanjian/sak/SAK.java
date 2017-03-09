package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.utils.Check;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {

    public static final int INFO_KEY = R.layout.sak_container_alyout;
    private static Manager sManager;

    private SAK() {
    }

    public static synchronized void init(Application application) {
        Check.isNull(application, "application");
        init(application, null);
    }

    public static synchronized void init(Application application, Config config) {
        Check.isNull(application, "application");
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
        if (sManager == null) {
            throw new RuntimeException("init first!");
        }
        Check.isNull(activity, "activity");
        sManager.attach(activity);
    }

    public static void detch(Activity activity) {
        Check.isNull(sManager, "init first!");
        Check.isNull(activity, "activity");
        sManager.detach(activity);
    }

//    public static void unInstall(Activity activity) {
//        Check.isNull(activity, "activity");
//        detch(activity);
//    }

}
