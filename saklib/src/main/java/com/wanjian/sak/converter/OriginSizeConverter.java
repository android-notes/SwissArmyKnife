package com.wanjian.sak.converter;

import android.content.Context;

import com.wanjian.sak.mess.Size;

/**
 * Created by wanjian on 2017/2/20.
 */

public class OriginSizeConverter extends SizeConverter {


    @Override
    public String desc() {
        return "Px";
    }

    @Override
    public Size convert(Context context, float length) {
        Size size = Size.obtain();

        size.setLength(length);
        size.setUnit("px");

        return size;
    }

}
