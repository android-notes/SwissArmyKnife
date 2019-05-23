package com.wanjian.sak.utils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class VerifyUtils {

    private static Class<?> nestScrollViewClz;
    private static Class<?> constraintLayoutClz;

    static {
        try {
            nestScrollViewClz = Class.forName("android.support.v4.widget.NestedScrollView");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            constraintLayoutClz = Class.forName("android.support.constraint.ConstraintLayout");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verify(View view) {
        if (view == null) {
            return false;
        }
        if (view instanceof ScrollView) {
            return false;
        }
        Class<?> viewClass = view.getClass();
        if (nestScrollViewClz != null && nestScrollViewClz.isAssignableFrom(viewClass)) {
            return false;
        }

        return (view instanceof FrameLayout) || (view instanceof RelativeLayout) || (constraintLayoutClz != null && constraintLayoutClz.isAssignableFrom(viewClass));
    }

}
