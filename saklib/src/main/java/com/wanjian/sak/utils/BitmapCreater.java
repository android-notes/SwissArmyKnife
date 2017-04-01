package com.wanjian.sak.utils;

import android.graphics.Bitmap;

/**
 * Created by wanjian on 2017/4/1.
 */

public class BitmapCreater {
    public static Bitmap create(int w, int h, Bitmap.Config config) {

        Check.isNull(config, "config");
        if (w <= 0) {
            throw new IllegalArgumentException("w can not be <= 0");
        }
        if (h <= 0) {
            throw new IllegalArgumentException("h can not be <= 0");
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
