package com.wanjian.sak;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.view.ContaierView;

import java.util.HashSet;
import java.util.Set;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by wanjian on 2016/10/23.
 */

public class CanvasManager {

    private Set<AbsCanvas> mCanvasList = new HashSet<>();
    private Paint defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int startLayer;
    private int endLayer = 30;

    public static final int INFO_KEY = R.layout.sak_container_alyout;
    private ViewGroup mViewGroup;
    private static CanvasManager sCanvasManager;
    private boolean needRefresh;

    private ContaierView contaierView;

    public synchronized static CanvasManager getInstance(Context context) {
        if (sCanvasManager == null) {
            sCanvasManager = new CanvasManager(context.getApplicationContext());
        }
        return sCanvasManager;
    }

    private CanvasManager(Context context) {
        defaultPaint.setTextSize(dp2px(context, 10));
        contaierView = new ContaierView(context);
    }

    public void attach(Activity activity) {
        if (contaierView.getParent() != null) {
            ((ViewGroup) contaierView.getParent()).removeView(contaierView);
        }

        mViewGroup = (ViewGroup) activity.getWindow().getDecorView();
        mViewGroup.addView(contaierView);
        if (SDK_INT >= 11) {//低版本android不支持
            activity.getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    contaierView.invalidate();
                }
            });
        }
    }

    public void detch(Activity activity) {
        ((ViewGroup) activity.getWindow().getDecorView()).removeView(contaierView);
        mViewGroup = null;
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
