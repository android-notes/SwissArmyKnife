package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public class OriginSizeConverter extends SizeConverter {


    @Override
    public String desc() {
        return "Origin(px)";
    }

    @Override
    public Size convert(Context context, float length) {
        Size size = Size.obtain();

        size.setLength(length);
        size.setUnit("px");

        return size;
    }

}
