package com.wanjian.sak.canvasimpl.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.AbsCanvas;
import com.wanjian.sak.view.ContaierView;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class CanvasLayerAdapter extends AbsCanvas {
    private int mStartLayer;
    private int mEndLayer;

    private int curLayer = -1;

    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        this.mStartLayer = startLayer;
        this.mEndLayer = endLayer;
        curLayer = -1;
        layerCount(canvas, view, paint);
    }

    private void layerCount(Canvas canvas, View view, Paint paint) {
        if (view == null || view.getVisibility() == View.GONE) {
            return;
        }
        if (curLayer + 1 > mEndLayer) {
            return;
        }
        curLayer++;
        if (curLayer >= mStartLayer && !(view instanceof ContaierView)) {
            drawLayer(canvas, paint, view);
        }

        if (view instanceof ViewGroup && !(view instanceof ContaierView)) {
            ViewGroup vg = ((ViewGroup) view);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                layerCount(canvas, child, paint);
            }
        }

        curLayer--;
    }

    protected int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    protected int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    protected abstract void drawLayer(Canvas canvas, Paint paint, View view);
}
