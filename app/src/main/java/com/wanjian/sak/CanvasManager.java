package com.wanjian.sak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanjian on 2016/10/23.
 */

public class CanvasManager {

    private Set<AbsCanvas> mCanvasList = new HashSet<>();
    private Paint defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int startLayer;
    private int endLayer = 30;

    public static final int INFO_KEY = 3 << 24;
    private ViewGroup mViewGroup;
    private static CanvasManager sCanvasManager;
    private boolean needRefresh;

    public synchronized static CanvasManager getInstance(Context context) {
        if (sCanvasManager == null) {
            sCanvasManager = new CanvasManager(context.getApplicationContext());
        }
        return sCanvasManager;
    }

    private CanvasManager(Context context) {
        defaultPaint.setTextSize(dp2px(context, 10));
    }


    public void setViewGroup(ViewGroup viewGroup) {
        mViewGroup = viewGroup;

    }

    public void setLayer(int start, int end) {
        startLayer = start;
        endLayer = end;
    }

    public CanvasManager addCanvas(AbsCanvas canvas) {
        mCanvasList.add(canvas);
        return this;
    }

    public void resetCanvas() {
        mCanvasList.clear();
    }
//    private void forceDraw() {
//        draw();
//    }

    public void draw(Canvas canvas) {
        for (AbsCanvas iCanvas : mCanvasList) {
            if (iCanvas == null) {
                continue;
            }
            iCanvas.onDraw(canvas, defaultPaint, mViewGroup, startLayer, endLayer);
        }
    }

    public void needRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public int getStartLayer() {
        return startLayer;
    }

    public int getEndLayer() {
        return endLayer;
    }

    public ViewGroup getViewGroup() {
        return mViewGroup;
    }

    private int dp2px(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

}
