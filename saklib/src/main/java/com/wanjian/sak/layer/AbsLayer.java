package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.ILayer;
import com.wanjian.sak.R;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.mess.Size;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class AbsLayer implements ILayer {

    protected Context mContext;
    private boolean mEnable;
    private Config config;

    public AbsLayer(Context context) {
        mContext = context;
    }

    public void attachConfig(Config config) {
        this.config = config;
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_launcher_icon);
    }

    @Override
    public void enable(boolean enable) {
        this.mEnable = enable;
    }

    @Override
    public boolean isEnable() {
        return mEnable;
    }

    public Context getContext() {
        return mContext;
    }

    protected int getStartRange() {
        return config.getStartRange();
    }

    protected int getEndRange() {
        return config.getEndRange();
    }
//    public final void drawIfOutBounds(boolean b) {
//        mDrawIfOutBounds = b;
//    }

    protected boolean isClipDraw() {
        return config.isClipDraw();
    }

    public final void draw(Canvas canvas, View view) {
        if (!mEnable) {
            return;
        }
        canvas.save();
        onDraw(canvas, view);
        canvas.restore();
    }

    protected abstract void onDraw(Canvas canvas, View view);

    protected int getColor() {
        return Color.BLACK;
    }

    /**
     * @param view view
     * @return x, y, width,height
     */
    protected int[] getLocationAndSize(View view) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        View decorView = view.getRootView();// dialog 内边距
        return new int[]{locations[0] - decorView.getPaddingLeft(), locations[1] - decorView.getPaddingTop(), view.getWidth(), view.getHeight()};
    }

    protected Size convertSize(float length) {
        return ISizeConverter.CONVERTER.convert(mContext, length);
    }

    protected int dp2px(float dip) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    protected int px2dp(float px) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
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
