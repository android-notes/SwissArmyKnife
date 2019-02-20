package com.wanjian.sak.utils;

import android.os.Handler;
import android.os.Looper;

public class UIHandler {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static Handler get() {
        return handler;
    }
}
