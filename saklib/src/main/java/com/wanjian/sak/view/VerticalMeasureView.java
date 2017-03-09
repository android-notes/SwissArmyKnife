package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.wanjian.sak.R;

/**
 * Created by wanjian on 2016/11/10.
 */

public class VerticalMeasureView extends HorizontalMeasureView {
    public VerticalMeasureView(Context context) {
        super(context);
    }


    @Override
    public String description() {
        return getContext().getString(R.string.sak_vertical_measure);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = dp2px(60);
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int h = getHeight();
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
