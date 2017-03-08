package com.wanjian.sak.canvasimpl.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.AbsLayer;
import com.wanjian.sak.ViewFilter;
import com.wanjian.sak.view.SAKCoverView;



/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class LayerAdapter extends AbsLayer {
    private int mStartLayer;
    private int mEndLayer;

    private int curLayer = -1;

    public LayerAdapter(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        this.mStartLayer = startLayer;
        this.mEndLayer = endLayer;
        curLayer = -1;
        layerCount(canvas, view, paint);
    }

    private void layerCount(Canvas canvas, View view, Paint paint) {
        if (view == null || view.getVisibility() == View.GONE|| ViewFilter.FILTER.filter(view)) {
            return;
        }
        if (curLayer + 1 > mEndLayer) {
            return;
        }
        curLayer++;
        if (curLayer >= mStartLayer && curLayer <= mEndLayer && !(view instanceof SAKCoverView)) {
            drawLayer(canvas, paint, view);
        }

        if (view instanceof ViewGroup && !(view instanceof SAKCoverView)) {
            ViewGroup vg = ((ViewGroup) view);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                layerCount(canvas, child, paint);
            }
        }
        curLayer--;
    }


    protected abstract void drawLayer(Canvas canvas, Paint paint, View view);
}
