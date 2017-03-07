package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Px2dpSizeConverter implements SizeConverter {
    @Override
    public Size convert(Context context, int length) {
        Size size = Size.obtain();

        size.setLength(px2dp(context, length));

        size.setUnit("dp");

        return size;
    }

    private int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
