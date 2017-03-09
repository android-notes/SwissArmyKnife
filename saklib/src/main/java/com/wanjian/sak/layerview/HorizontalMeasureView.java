package com.wanjian.sak.layerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/11/10.
 */

public class HorizontalMeasureView extends DragLayerView {
    public HorizontalMeasureView(Context context) {
        super(context);
        init();
    }


    @Override
    public String description() {
        return getContext().getString(R.string.sak_horizontal_measure);
    }


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


    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = dp2px(60);
        return params;
    }


    @Override
    public void onChange(MotionEvent motionEvent) {

    }
}
