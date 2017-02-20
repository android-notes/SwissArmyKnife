package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.wanjian.sak.layerview.LayerView;

import static android.R.attr.maxHeight;
import static android.R.attr.minHeight;

/**
 * Created by wanjian on 2016/11/10.
 */

public class VerticalMeasureView extends HorizontalMeasureView {
    public VerticalMeasureView(Context context) {
        super(context);
    }

    public VerticalMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected String description() {
        return "竖直直尺";
    }



    @Override
    protected void onDraw(Canvas canvas) {

        int h = getHeight();
        int w = getWidth();
        canvas.translate(maxHeight, 0);
        canvas.drawLine(0, 0, 0, h, mPaint);

        for (int i = 0; i <= h; i += 5) {
            if (i % 10 == 0) {
                canvas.drawLine(-maxHeight, i, maxHeight, i, mPaint);
            } else {
                canvas.drawLine(-minHeight, i, minHeight, i, mPaint);
            }

            if (i % (oneDP * 20) == 0) {
                canvas.drawLine(-maxHeight * 2f, i, maxHeight * 2f, i, mPaint);
                canvas.drawText(String.valueOf(i) + "/" + px2dp(i), maxHeight, i + mPaint.getTextSize() / 2, mPaint);
            }
        }

    }
}
