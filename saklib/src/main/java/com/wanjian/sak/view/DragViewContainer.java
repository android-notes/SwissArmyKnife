package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by wanjian on 2016/11/10.
 */

public class DragViewContainer extends FrameLayout {
    public DragViewContainer(Context context) {
        super(context);
    }

    public DragViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private float lastX;
    private float lastY;

    private float pressX;
    private float pressY;

    private long pressTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getRawX();
            lastY = event.getRawY();
            pressX = lastX;
            pressY = lastY;
            pressTime = System.currentTimeMillis();
        } else {
            float curX = event.getRawX();
            float curY = event.getRawY();
            ViewGroup.LayoutParams params = getLayoutParams();
            if (!(params instanceof MarginLayoutParams)) {
                return super.onTouchEvent(event);
            }
            MarginLayoutParams marginLayoutParams = ((MarginLayoutParams) params);
            marginLayoutParams.leftMargin += (curX - lastX);
            marginLayoutParams.topMargin += (curY - lastY);
            setLayoutParams(marginLayoutParams);
            lastX = curX;
            lastY = curY;

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (System.currentTimeMillis() - pressTime > 2 * 1000) {
                float curX = event.getRawX();
                float curY = event.getRawY();
                int dis = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (Math.abs(curX - pressX) <= dis && Math.abs(curY - pressY) <= dis) {
//                    ((ViewGroup) getParent()).removeView(this);
                }
            }
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof OnMoveListener) {
                OnMoveListener moveListener = ((OnMoveListener) child);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        moveListener.down(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveListener.move(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        moveListener.up(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        moveListener.cancel(event.getX(), event.getY());
                        break;
                }
            }
        }

        return true;
    }

    interface OnMoveListener {
        void down(float x, float y);

        void move(float x, float y);

        void up(float x, float y);

        void cancel(float x, float y);
    }
}
