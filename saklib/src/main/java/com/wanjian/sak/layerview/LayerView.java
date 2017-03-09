package com.wanjian.sak.layerview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.OnChangeListener;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class LayerView extends View implements OnChangeListener {
    private boolean mEnable;

    public LayerView(Context context) {
        super(context);
    }

    /**
     * @param enable
     * @hide
     */
    public void mEnable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean isEnable() {
        return mEnable;
    }


    public abstract String description();

    private float lastX;
    private float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getRawX();
            lastY = event.getRawY();
        } else {
            float curX = event.getRawX();
            float curY = event.getRawY();
            ViewGroup.LayoutParams params = getLayoutParams();
            if (!(params instanceof ViewGroup.MarginLayoutParams)) {
                return super.onTouchEvent(event);
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
            marginLayoutParams.leftMargin += (curX - lastX);
            marginLayoutParams.topMargin += (curY - lastY);
            requestLayout();
            lastX = curX;
            lastY = curY;

        }
        onChange(event);
        return true;
    }

    protected int dp2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    protected int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public abstract ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params);
}
