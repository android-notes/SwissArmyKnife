package com.wanjian.sak.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by wanjian on 2016/10/23.
 */

public class FloatView extends View {
    public FloatView(Context context) {
        super(context);
        init();
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() << 1;
    }

    private float mLastX;
    private float mLastY;
    private Boolean mIsDrag;
    private int mTouchSlop;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsDrag = null;
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = event.getRawX();
                float curY = event.getRawY();
                if (mIsDrag == null) {
                    if (Math.pow(curX - mLastX, 2) + Math.pow(curY - mLastY, 2) > mTouchSlop) {
                        mIsDrag = true;
                        mLastX = curX;
                        mLastY = curY;
                        break;
                    }
                } else if (mIsDrag) {
                    ViewGroup.LayoutParams params = getLayoutParams();
                    if (!(params instanceof ViewGroup.MarginLayoutParams)) {
                        return super.onTouchEvent(event);
                    }
                    ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
                    marginLayoutParams.leftMargin += (curX - mLastX);
                    marginLayoutParams.topMargin += (curY - mLastY);
                    requestLayout();
                    mLastX = curX;
                    mLastY = curY;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsDrag == null) {
                    performClick();
                }
                moveIfNeeded();
                break;
        }

        return true;
    }

    private void moveIfNeeded() {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
        if (marginLayoutParams.leftMargin < 0) {
            marginLayoutParams.leftMargin = 0;
        }
        if (marginLayoutParams.topMargin < dp2px(30)) {
            marginLayoutParams.topMargin = dp2px(30);
        }
        if (marginLayoutParams.leftMargin + getWidth() > ((View) getParent()).getWidth()) {
            marginLayoutParams.leftMargin = ((View) getParent()).getWidth() - getWidth();
        }
        if (marginLayoutParams.topMargin + getHeight() > ((View) getParent()).getHeight()) {
            marginLayoutParams.topMargin = ((View) getParent()).getHeight() - getHeight();
        }
        requestLayout();
    }


    private int dp2px(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

}
