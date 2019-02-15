package com.wanjian.sak.layerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wanjian.sak.ILayer;
import com.wanjian.sak.R;
import com.wanjian.sak.config.Config;

/**
 * Created by wanjian on 2017/3/9.
 */

public abstract class AbsLayerView extends FrameLayout implements ILayer {
    private boolean mEnable;
    private Config config;

    public AbsLayerView(Context context) {
        super(context);
    }

    public void attachConfig(Config config) {
        this.config = config;
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_launcher_icon);
    }

    @Override
    public void enable(boolean enable) {
        this.mEnable = enable;
    }

    @Override
    public boolean isEnable() {
        return mEnable;
    }


    protected int getStartRange() {
        return config.getStartRange();
    }

    protected int getEndRange() {
        return config.getEndRange();
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
