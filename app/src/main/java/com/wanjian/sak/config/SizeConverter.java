package com.wanjian.sak.config;

import android.content.Context;

/**
 * Created by wanjian on 2017/2/20.
 */

public interface SizeConverter {
    SizeConverter DEFAULT = new DefaultSizeConverter();

    Size convert(Context context, int length);
}
