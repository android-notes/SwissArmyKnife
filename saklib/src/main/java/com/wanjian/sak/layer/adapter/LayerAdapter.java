package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.view.SAKCoverView;


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
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        this.mStartLayer = startLayer;
        this.mEndLayer = endLayer;
        mCurLayer = -1;
        layerCount(canvas, view, paint);
    }

    private void layerCount(Canvas canvas, View view, Paint paint) {
        if (view == null || view instanceof SAKCoverView) {
            return;
        }
        if (mCurLayer + 1 > mEndLayer) {
            return;
        }
        mCurLayer++;
        if (mCurLayer >= mStartLayer && mCurLayer <= mEndLayer) {
            if (ViewFilter.FILTER.filter(view)) {
                drawLayer(canvas, paint, view);
            }
        }

        if (view instanceof ViewGroup) {
            ViewGroup vg = ((ViewGroup) view);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                layerCount(canvas, child, paint);
            }
        }
        mCurLayer--;
    }


    protected abstract void drawLayer(Canvas canvas, Paint paint, View view);
}
