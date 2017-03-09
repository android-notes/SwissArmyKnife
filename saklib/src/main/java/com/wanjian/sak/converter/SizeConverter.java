package com.wanjian.sak.converter;

import android.content.Context;

import com.wanjian.sak.mess.Size;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class SizeConverter {
    public abstract String desc();

    public static SizeConverter CONVERTER = new Px2dpSizeConverter();

    public abstract Size convert(Context context, float length);

}
