package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public class DefaultSizeConverter implements SizeConverter {


    @Override
    public String desc() {
        return "Origin(px)";
    }

    @Override
    public Size convert(Context context, int length) {

        Size size = Size.obtain();

        size.setLength(length);
        size.setUnit("px");

        return size;
    }
}
