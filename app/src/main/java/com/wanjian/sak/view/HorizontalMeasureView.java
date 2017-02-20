package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wanjian.sak.layerview.LayerView;


/**
 * Created by wanjian on 2016/11/10.
 */

public class HorizontalMeasureView extends LayerView {
    public HorizontalMeasureView(Context context) {
        super(context);
        init();
    }


    @Override
    protected String description() {
        return "水平直尺";
    }



    protected final String TAG = "HorizontalMeasureView";
    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected int oneDP;

    protected int maxHeight;
    protected int minHeight;


    private void init() {
        mPaint.setColor(Color.BLACK);
        oneDP = dp2px(1);
        mPaint.setTextSize(dp2px(8));
//        mPaint.setTextSize(dp2px(12));
        maxHeight = dp2px(10);
        minHeight = dp2px(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = getHeight();
        int w = getWidth();
//        canvas.clipRect(0,0,w,h);
        canvas.translate(0, maxHeight);
        canvas.drawLine(0, 0, w, 0, mPaint);

        for (int i = 0; i <= w; i += 5) {
            if (i % 10 == 0) {
                canvas.drawLine(i, -maxHeight, i, maxHeight, mPaint);
            } else {
                canvas.drawLine(i, -minHeight, i, minHeight, mPaint);
            }

            if (i % (oneDP * 20) == 0) {
                canvas.drawLine(i, -maxHeight * 2f, i, maxHeight * 2f, mPaint);
                canvas.rotate(90, i, maxHeight);
                canvas.drawText(String.valueOf(i) + "/" + px2dp(i), i, maxHeight + mPaint.getTextSize() / 2, mPaint);
                canvas.rotate(-90, i, maxHeight);
            }
        }

    }

    protected int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    protected int dp2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
