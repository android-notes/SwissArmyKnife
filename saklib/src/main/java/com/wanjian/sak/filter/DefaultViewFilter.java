package com.wanjian.sak.filter;

import android.view.View;

import com.wanjian.sak.filter.ViewFilter;

/**
 * Created by wanjian on 2017/2/20.
 */

public class DefaultViewFilter extends ViewFilter {
    @Override
    public boolean apply(View view) {

        return view.getVisibility() == View.VISIBLE;
    }


}
