package com.wanjian.sak;

import android.app.Application;
import android.os.Looper;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.utils.Check;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {
    public static final int INFO_KEY = R.layout.sak_container_layout;
    static final int KEY = R.layout.sak_layer_item;
    private static boolean isInstalled = false;

    private static Inject inject;

    public static void init(Application application) {
        init(application, null);
    }

    public static void init(Application application, Config config) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("init on ui thread !");
        }
        if (application == null) {
            Check.isNull(application, "application");
            return;
        }
        if (isInstalled) {
            return;
        }
        isInstalled = true;

        if (config == null) {
            config = new Config.Build(application).build();
        }

        inject = new Inject();
        inject.install(application, config);
    }

    public static void unInstall() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("unInstall on ui thread !");
        }
        if (isInstalled == false) {
            return;
        }
        isInstalled = false;

        inject.unInstall();
        inject = null;
    }

}
