package com.wanjian.sak.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wanjian on 2017/4/1.
 */

public class MyView extends View {
    Handler mHandler = new Handler();
    int v = 0;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            v++;
            invalidate();
            mHandler.postDelayed(this, 1000);
        }
    };
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setTextSize(50);
        mPaint.setColor(Color.RED);
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("" + v, 10, getHeight() / 2, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(runnable);
    }
}
