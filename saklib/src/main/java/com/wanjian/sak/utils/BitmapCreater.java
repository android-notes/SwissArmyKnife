package com.wanjian.sak.utils;

import android.graphics.Bitmap;

/**
 * Created by wanjian on 2017/4/1.
 */

public class BitmapCreater {
    public static Bitmap create(int w, int h, Bitmap.Config config) {

        Check.isNull(config, "config");
        if (w <= 0) {
            new IllegalArgumentException("w can not be <= 0").printStackTrace();
            return null;
        }
        if (h <= 0) {
            new IllegalArgumentException("h can not be <= 0").printStackTrace();
            return null;
        }
        for (int i = 0; i < 3; i++) {
            try {
                return Bitmap.createBitmap(w, h, config);
            } catch (Throwable e) {
                Runtime.getRuntime().gc();
            }
        }
        return null;
    }
}
