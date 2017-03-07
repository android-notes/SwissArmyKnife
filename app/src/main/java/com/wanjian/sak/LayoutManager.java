package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.view.ContaierView;


/**
 * Created by wanjian on 2016/10/23.
 */

public class LayoutManager {
    public static final int INFO_KEY = CanvasManager.INFO_KEY;

    /**
     * API 14(ICE_CREAM_SANDWICH) 及以上可以在application的onCreate中调用该方法
     */
    public static void init(Application context) {
        if (context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        attach(activity);
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        detch(activity);
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                    }
                });
            } else {
                System.err.println("shouldn't  call init(Context ctx) before API 14");
            }
        }
    }

    private static void attach(Activity activity) {
        CanvasManager.getInstance(activity).attach(activity);
    }

    private static void detch(Activity activity) {
        CanvasManager.getInstance(activity).detch(activity);
    }

    public static void pause(Activity activity) {
        detch(activity);
    }

    public static void resume(Activity activity) {
        attach(activity);
    }

//    private static void init(Activity activity) {
//
//        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
//        CanvasManager.getInstance(activity.getApplicationContext()).setViewGroup(dectorView);
//
//        int i;
//        for (i = dectorView.getChildCount() - 1; i > -1; i--) {
//            View v = dectorView.getChildAt(i);
//            if (v instanceof ContaierView) {
//                break;
//            }
//        }
//
//        if (i == -1) {
//            ContaierView contaierView = new ContaierView(activity);
//            dectorView.addView(contaierView);
//        }
//        dectorView.requestLayout();
//    }

    private LayoutManager() {
    }
}
