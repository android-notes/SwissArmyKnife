package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.layer.AbsLayer;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class LayerAdapter extends AbsLayer {
    private int mStartLayer;
    private int mEndLayer;
    private boolean isClip;

    public LayerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onUiUpdate(Canvas canvas, View rootView) {
        mStartLayer = getStartRange();
        mEndLayer = getEndRange();
        isClip = isClipDraw();
        layerCount(canvas, rootView, 0);
    }


    private void layerCount(Canvas canvas, View view, int curLayer) {
        if (!getViewFilter().filter(view)) {
            return;
        }
        if (curLayer > mEndLayer) {
            return;
        }
        canvas.save();
        if (curLayer >= mStartLayer) {
            drawLayer(canvas, view, curLayer);
        }
        if (!(view instanceof ViewGroup)) {
            canvas.restore();
            return;
        }

        clipIfNeeded(canvas, view, curLayer);

        ViewGroup vg = ((ViewGroup) view);
        for (int i = 0, len = vg.getChildCount(); i < len; i++) {
            View child = vg.getChildAt(i);
            layerCount(canvas, child, curLayer + 1);
        }

        canvas.restore();
    }

    private void clipIfNeeded(Canvas canvas, View view, int curLayer) {
        if (!isClip) {
            return;
        }

        int w = view.getWidth();
        int h = view.getHeight();
        canvas.clipRect(0
                , 0
                , w
                , h
                , Region.Op.INTERSECT);
    }


    private void drawLayer(Canvas canvas, View view, int curLayer) {
        int pl = 0;
        int pt = 0;
        if (curLayer == 1) {
            View decorView = (View) view.getParent();
            pl = decorView.getPaddingLeft();
            pt = decorView.getPaddingTop();
        }//view.getLocationInWindow();
        canvas.translate(view.getX() - pl - view.getScrollX(), view.getY() - pt - view.getScrollY());
        int count = canvas.save();
        onDrawLayer(canvas, view);
        canvas.restoreToCount(count);
    }

    protected abstract void onDrawLayer(Canvas canvas, View view);
}
