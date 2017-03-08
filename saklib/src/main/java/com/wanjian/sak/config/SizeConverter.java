package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class SizeConverter {
    abstract String desc();

    public  static SizeConverter CONVERTER = new Px2dpSizeConverter();

    abstract Size convert(Context context, int length);
}
