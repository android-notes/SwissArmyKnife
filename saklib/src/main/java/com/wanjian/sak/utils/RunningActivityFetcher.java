package com.wanjian.sak.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunningActivityFetcher {

    private static Map mActivities;  //ActivityThread.mActivities<IBinder, ActivityClientRecord>
    private static Field activityField;//ActivityClientRecord.activity

    public static List<WeakReference<Activity>> fetch() {

        if (mActivities == null) {
            getmActivities();
        }
        List<WeakReference<Activity>> references = new ArrayList<>();

        for (Object actRecord : mActivities.values()) {
            if (activityField == null) {
                getActivityField(actRecord);
            }
            if (activityField == null) {
                return null;
            }

            try {
                Activity activity = (Activity) activityField.get(actRecord);
                references.add(new WeakReference<>(activity));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
        return references;
    }

    private static void getActivityField(Object actRecord) {
        try {
            activityField = actRecord.getClass().getDeclaredField("activity");
            activityField.setAccessible(true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void getmActivities() {
        try {
            Class activityThreadClz = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThread = currentActivityThreadMethod.invoke(null);
            Field mActivitiesField = activityThreadClz.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            //<IBinder, ActivityClientRecord>
            mActivities = (Map) mActivitiesField.get(activityThread);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
