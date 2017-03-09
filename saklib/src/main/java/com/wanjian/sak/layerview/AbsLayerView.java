package com.wanjian.sak.layerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wanjian on 2017/3/9.
 */

public abstract class AbsLayerView extends View {
    private boolean mEnable;

    public AbsLayerView(Context context) {
        super(context);
    }

    public abstract String description();

    public void enable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean isEnable() {
        return mEnable;
    }

    protected int dp2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    protected int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public abstract ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params);


}
