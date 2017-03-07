package com.wanjian.sak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.config.Color;
import com.wanjian.sak.config.Size;
import com.wanjian.sak.config.SizeConverter;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class AbsLayer extends ILayer {

    protected Context mContext;
    private Paint mPaint;
    private boolean enable;

    public AbsLayer(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getColor());
    }

    public abstract String description();

    public void enable(boolean enable) {
        this.enable = enable;
    }

    public final void draw(Canvas canvas, View view, int startLayer, int endLayer) {
        if (!enable) {
            return;
        }
        onDraw(canvas, mPaint, view, startLayer, endLayer);
    }

    protected abstract void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer);

    protected int getColor() {
        return Color.BLACK;
    }

    /**
     * @param view
     * @return x, y, width,height
     */
    protected int[] getLocationAndSize(View view) {
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        return new int[]{locations[0], locations[1], view.getWidth(), view.getHeight()};
    }

    protected Size convertSize(int length) {
        return SizeConverter.DEFAULT.convert(mContext, length);
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
