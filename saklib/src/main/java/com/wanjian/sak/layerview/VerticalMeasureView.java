package com.wanjian.sak.layerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wanjian.sak.R;

/**
 * Created by wanjian on 2016/11/10.
 */

public class VerticalMeasureView extends HorizontalMeasureView {
    public VerticalMeasureView(Context context) {
        super(context);
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_ver_measure_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_vertical_measure);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = dp2px(60);
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        if (params instanceof FrameLayout.LayoutParams) {
            ((LayoutParams) params).gravity = Gravity.CENTER;
        }
        return params;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setStyle(Paint.Style.FILL);

        int h = getHeight();
        canvas.translate(maxHeight, 0);
        canvas.drawLine(0, 0, 0, h, mPaint);

        for (int i = 0; i <= h; i += mTwoDP) {
            canvas.drawLine(-minHeight, i, minHeight, i, mPaint);

            if ((i / mTwoDP << 1) % 20 == 0) {
                canvas.drawLine(-maxHeight * 2f, i, maxHeight * 2f, i, mPaint);
                canvas.drawText(String.valueOf(i) + "/" + (i / mTwoDP << 1), maxHeight, i + mPaint.getTextSize() / 2, mPaint);
            }
        }

    }
}
