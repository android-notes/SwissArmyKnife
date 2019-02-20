package com.wanjian.sak.converter;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Px2dpSizeConverter extends ISizeConverter {
    @Override
    public String desc() {
        return "Dp";
    }

    @Override
    public Size convert(Context context, float length) {
        Size size = Size.obtain();

        size.setLength(px2dp(context, length));

        size.setUnit("dp");

        return size;
    }

    @Override
    public int recovery(Context context, float length) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (length * scale + 0.5f);
    }

    private int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
