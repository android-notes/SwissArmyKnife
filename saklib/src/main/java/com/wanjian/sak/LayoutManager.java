package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.view.SAKCoverView;


/**
 * Created by wanjian on 2016/10/23.
 */

@Deprecated
public class LayoutManager {

    public static void init(Application context) {
        SAK.init(context);
    }


    public static void init(Activity activity) {
        SAK.attach(activity);

    }

    private LayoutManager() {
    }
}
