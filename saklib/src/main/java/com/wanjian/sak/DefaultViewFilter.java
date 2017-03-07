package com.wanjian.sak;

import android.view.View;

import com.wanjian.sak.view.SAKCoverView;

/**
 * Created by wanjian on 2017/2/20.
 */

public class DefaultViewFilter extends ViewFilter {
    @Override
    public boolean apply(View view) {
        if (view instanceof SAKCoverView) {
            return false;
        }
        return true;
    }

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
