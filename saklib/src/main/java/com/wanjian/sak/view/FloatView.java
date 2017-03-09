package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    }

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getRawX();
            downY = event.getRawY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int s = dp2px(5);
            if (Math.abs(event.getRawX() - downX) <= s && Math.abs(event.getRawY() - downY) <= s) {
                performClick();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ViewGroup.LayoutParams param = getLayoutParams();
            if (param instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) param);

                marginLayoutParams.leftMargin = (int) event.getRawX() - getWidth() / 2;
                marginLayoutParams.topMargin = (int) event.getRawY() - getHeight() / 2;
                int dp30 = dp2px(30);
                int w = getWindwosWidth() - dp30;
                if (marginLayoutParams.leftMargin > w) {
                    marginLayoutParams.leftMargin = w;
                }

                int h = getWindwosHeight() - dp30;
                if (marginLayoutParams.topMargin > h) {
                    marginLayoutParams.topMargin = h;
                }

                if (marginLayoutParams.topMargin < dp30) {
                    marginLayoutParams.topMargin = dp30;
                }
                setLayoutParams(marginLayoutParams);
            }

        }
        return true;
    }

    private int dp2px(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    private int getWindwosWidth() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    private int getWindwosHeight() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


}
