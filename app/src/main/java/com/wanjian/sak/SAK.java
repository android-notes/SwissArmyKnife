package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.view.ContaierView;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {


    private static SAK sSAK;

    private Config mConfig;

    private Context mContext;
    private ContaierView contaierView;

    private SAK() {
    }

    private SAK(Context context, Config config) {
        mContext = context.getApplicationContext();
        mConfig = config;
        contaierView = new ContaierView(context);
    }

    public static synchronized SAK getInstance(Context context) {
        CheckNull.check(context, "context");
        if (sSAK == null) {
            sSAK = new SAK(context.getApplicationContext(), new Config.Build().build());
        }

        return sSAK;
    }

    public void regist(Activity activity) {
        CheckNull.check(activity, "activity");
        attach(activity);
    }

    public void regist(Application application) {
        CheckNull.check(application, "application");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
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
                    detach(activity);
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
            Log.w("SAK", "should not use regist(Application application) below API14 (ICE_CREAM_SANDWICH),you can use regist(Activity activity) ");
        }
    }

    public void unRegist() {
        sSAK = null;
    }

    private void detach(Activity activity) {
        contaierView.detach(activity);
    }

    private void attach(Activity activity) {
        contaierView.attach(activity);
        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
        dectorView.addView(contaierView);
    }


    public void applyConfig(Config config) {
        CheckNull.check(config, "config");
        mConfig = config;
    }
}
