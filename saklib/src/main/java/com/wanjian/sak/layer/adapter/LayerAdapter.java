package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.view.RootContainerView;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class LayerAdapter extends AbsLayer {
    private int mStartLayer;
    private int mEndLayer;

    private int mCurLayer = -1;

    public LayerAdapter(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas, View view) {
        this.mStartLayer = getStartRange();
        this.mEndLayer = getEndRange();
        mCurLayer = -1;
        layerCount(canvas, view);
    }

    private void layerCount(Canvas canvas, View view) {
        if (view == null || view instanceof RootContainerView || ViewFilter.FILTER.filter(view) == false) {
            return;
        }
        if (mCurLayer + 1 > mEndLayer) {
            return;
        }
        boolean drawIfOutOfBounds = !isClipDraw();
        int count = 0;
        ViewParent parent = view.getParent();
        if (drawIfOutOfBounds == false) {
            if (parent instanceof View) {
                count = canvas.save();
                int[] locationAndSize = getLocationAndSize(((View) parent));
                canvas.clipRect(locationAndSize[0]
                        , locationAndSize[1]
                        , locationAndSize[0] + locationAndSize[2]
                        , locationAndSize[1] + locationAndSize[3]
                        , Region.Op.INTERSECT);
            }
        }
        mCurLayer++;
        if (mCurLayer >= mStartLayer && mCurLayer <= mEndLayer) {
            drawLayer(canvas, view);
        }

        if (view instanceof ViewGroup) {
            ViewGroup vg = ((ViewGroup) view);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                layerCount(canvas, child);
            }
        }
        mCurLayer--;
        if (drawIfOutOfBounds == false) {
            if (parent instanceof View) {
                canvas.restoreToCount(count);
            }
        }
    }


    protected abstract void drawLayer(Canvas canvas, View view);
}
