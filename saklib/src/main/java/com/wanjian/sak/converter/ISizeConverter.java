package com.wanjian.sak.converter;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class ISizeConverter {
    public static ISizeConverter CONVERTER = new Px2DpSizeConverter();

    public abstract String desc();

    public abstract Size convert(Context context, float length);

    public abstract int recovery(Context context, float length);

}
