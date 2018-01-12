package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;


/**
 * Created by wanjian on 2016/10/23.
 */

@Deprecated
public class LayoutManager {

    private LayoutManager() {
    }

    public static void init(Application context) {
        SAK.init(context);
    }

    public static void init(Activity activity) {
        SAK.resume(activity);

    }
}
