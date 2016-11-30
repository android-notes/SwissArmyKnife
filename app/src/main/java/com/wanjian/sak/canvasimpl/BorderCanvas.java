package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class BorderCanvas extends CanvasLayerAdapter {

    private int[] mLocation = new int[2];
    private int cornerW;
    private int strokeW;

    private int w;
    private int h;

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        cornerW = dp2px(6, view.getContext());
        strokeW = dp2px(1, view.getContext());
        w = view.getWidth();
        h = view.getHeight();
        view.getLocationOnScreen(mLocation);
        drawBorder(canvas, view, paint);
        drawCorner(canvas, view, paint);
    }

    private int dp2px(int dip, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    private void drawBorder(Canvas canvas, View v, Paint mPaint) {
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mLocation[0], mLocation[1], mLocation[0] + w, mLocation[1] + h, mPaint);
    }

    private void drawCorner(Canvas canvas, View v, Paint mPaint) {
        mPaint.setColor(Color.BLUE);
        float sw = mPaint.getStrokeWidth();
        mPaint.setStrokeWidth(strokeW);

        canvas.drawLine(mLocation[0], mLocation[1], mLocation[0] + cornerW, mLocation[1], mPaint);
        canvas.drawLine(mLocation[0], mLocation[1], mLocation[0], mLocation[1] + cornerW, mPaint);

        canvas.drawLine(mLocation[0] + w - cornerW, mLocation[1], mLocation[0] + w, mLocation[1], mPaint);
        canvas.drawLine(mLocation[0] + w, mLocation[1], mLocation[0] + w, mLocation[1] + cornerW, mPaint);

        canvas.drawLine(mLocation[0], mLocation[1] + h, mLocation[0], mLocation[1] + h - cornerW, mPaint);
        canvas.drawLine(mLocation[0], mLocation[1] + h, mLocation[0] + cornerW, mLocation[1] + h, mPaint);

        canvas.drawLine(mLocation[0] + w - cornerW, mLocation[1] + h, mLocation[0] + w, mLocation[1] + h, mPaint);
        canvas.drawLine(mLocation[0] + w, mLocation[1] + h - cornerW, mLocation[0] + w, mLocation[1] + h, mPaint);
        mPaint.setStrokeWidth(sw);

    }

}
