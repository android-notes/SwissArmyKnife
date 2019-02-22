package com.wanjian.sak.filter;

import android.view.View;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class ViewFilter {
    public static ViewFilter FILTER;

    public final boolean filter(View view) {
        return false;
    }

    protected abstract boolean apply(View view);
}
