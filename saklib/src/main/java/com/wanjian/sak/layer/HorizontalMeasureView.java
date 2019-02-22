package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wanjian.sak.R;
import com.wanjian.sak.support.DragLayerView;


/**
 * Created by wanjian on 2016/11/10.
 */

public class HorizontalMeasureView extends DragLayerView {
    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected int mTwoDP;
    protected int maxHeight;
    protected int minHeight;

    public HorizontalMeasureView(Context context) {
        super(context);
        init();
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_hori_measure_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_horizontal_measure);
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(Color.BLACK);
        mTwoDP = dp2px(2);
        mPaint.setTextSize(dp2px(8));
        maxHeight = dp2px(10);
        minHeight = dp2px(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        int w = getWidth();
        canvas.translate(0, maxHeight);
        canvas.drawLine(0, 0, w, 0, mPaint);

        for (int i = 0; i <= w; i += mTwoDP) {
            canvas.drawLine(i, -minHeight, i, minHeight, mPaint);
            if ((i / mTwoDP << 1) % 20 == 0) {
                canvas.drawLine(i, -maxHeight * 2f, i, maxHeight * 2f, mPaint);
                canvas.rotate(90, i, maxHeight);
                canvas.drawText(String.valueOf(i) + "/" + (i / mTwoDP << 1), i, maxHeight + mPaint.getTextSize() / 2, mPaint);
                canvas.rotate(-90, i, maxHeight);
            }
        }

    }


    @Override
    public ViewGroup.LayoutParams getLayoutParams(FrameLayout.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = dp2px(60);
        params.gravity = Gravity.CENTER;
        return params;
    }
}
