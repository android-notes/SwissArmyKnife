package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.converter.SizeConverter;
import com.wanjian.sak.mess.Size;
import com.wanjian.sak.utils.Color;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class AbsLayer {

    protected Context mContext;
    private Paint mPaint;
    private boolean mEnable;
    private boolean mDrawIfOutBounds;

    public AbsLayer(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(getColor());
    }

    public abstract String description();

    public void enable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean isEnable() {
        return mEnable;
    }


    public final void drawIfOutBounds(boolean b) {
        mDrawIfOutBounds = b;
    }

    protected boolean isDrawIfOutBounds() {
        return mDrawIfOutBounds;
    }

    public final void draw(Canvas canvas, View view, int startLayer, int endLayer) {
        if (!mEnable) {
            return;
        }
        canvas.save();
        onDraw(canvas, mPaint, view, startLayer, endLayer);
        canvas.restore();
    }

    protected abstract void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer);

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
        return SizeConverter.CONVERTER.convert(mContext, length);
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
