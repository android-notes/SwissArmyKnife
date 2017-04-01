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
    public MyView(Context context) {
        super(context);
        init();
    }

    Handler mHandler = new Handler();

    int v = 0;


    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void init() {
        mPaint.setTextSize(50);
        mPaint.setColor(Color.RED);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v++;
                invalidate();
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("" + v, 10, getHeight() / 2, mPaint);
    }
}
