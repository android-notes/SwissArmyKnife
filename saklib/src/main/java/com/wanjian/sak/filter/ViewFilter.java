package com.wanjian.sak.filter;

import android.view.View;

import com.wanjian.sak.view.SAKCoverView;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class ViewFilter {
    public static ViewFilter FILTER = new ViewFilter() {
        @Override
        protected boolean apply(View view) {
            return view.getVisibility() == View.VISIBLE;
        }
    };


    public final boolean filter(View view) {
        if (view instanceof SAKCoverView) {
            return false;
        }
        return apply(view);
    }

    protected abstract boolean apply(View view);

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.getClass() == getClass();
    }
}
