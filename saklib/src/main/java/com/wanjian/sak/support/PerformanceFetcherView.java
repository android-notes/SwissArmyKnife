package com.wanjian.sak.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class PerformanceFetcherView extends FrameLayout {
    private PerformanceListener listener;


    public PerformanceFetcherView(@NonNull Context context) {
        this(context, null);
    }

    public PerformanceFetcherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        long s = System.currentTimeMillis();
        super.dispatchDraw(canvas);
        if (listener != null) {
            listener.onDraw(System.currentTimeMillis() - s);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        long s = System.currentTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (listener != null) {
            listener.onMeasure(System.currentTimeMillis() - s);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        long s = System.currentTimeMillis();
        super.onLayout(changed, left, top, right, bottom);
        if (listener != null) {
            listener.onLayout(System.currentTimeMillis() - s);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        long s = System.currentTimeMillis();
        boolean b = super.dispatchTouchEvent(ev);
        if (listener != null) {
            listener.onTouch(System.currentTimeMillis() - s);
        }
        return b;
    }

    public void setPerformanceListener(PerformanceListener l) {
        listener = l;
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("can not add view by user");
        }
        super.addView(child, index, params);
    }

    public interface PerformanceListener {
        void onMeasure(long duration);

        void onLayout(long duration);

        void onDraw(long duration);

        void onTouch(long duration);
    }
}