package com.wanjian.sak;

import android.app.Application;

import com.wanjian.sak.config.Config;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {
    private static Scaffold sScaffold;

    public static void init(Application application, Config config) {
        if (sScaffold != null) {
            return;
        }
        sScaffold = new Scaffold();
        sScaffold.install(application, config);
    }

    private SAK() {
    }

    public static void unInstall() {
        if (sScaffold != null) {
            sScaffold.unInstall();
            sScaffold = null;
        }
    }

}
