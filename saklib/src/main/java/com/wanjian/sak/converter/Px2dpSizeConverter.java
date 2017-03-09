package com.wanjian.sak.converter;

import android.content.Context;

import com.wanjian.sak.mess.Size;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Px2dpSizeConverter extends SizeConverter {
    @Override
    public String desc() {
        return "Dp(pt)";
    }

    @Override
    public Size convert(Context context, float length) {
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
