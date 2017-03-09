package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/3/9.
 */

public class Px2SpSizeConverter extends SizeConverter {
    @Override
    public String desc() {
        return "Sp";
    }

    @Override
    public Size convert(Context context, float length) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return Size.obtain().setLength((int) (length / fontScale + 0.5f)).setUnit("sp");
    }
}
