package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;
import com.wanjian.sak.utils.Color;


/**
 * Created by wanjian on 2016/10/24.
 */

public class BorderLayer extends LayerAdapter {

    private int[] mLocationSize;
    private int mCornerW;
    private int mStrokeW;

    private int w;
    private int h;

    public BorderLayer(Context context) {
        super(context);
        enable(true);
    }

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        mCornerW = dp2px(6);
        mStrokeW = dp2px(1);
        w = view.getWidth();
        h = view.getHeight();
        mLocationSize = getLocationAndSize(view);
        paint.setColor(getBorderColor());
        drawBorder(canvas, view, paint);
        paint.setColor(getCornerColor());
        drawCorner(canvas, view, paint);
    }

    protected int getBorderColor() {
        return Color.BLUE;
    }

    protected int getCornerColor() {
        return Color.MAGENTA;
    }

    private void drawBorder(Canvas canvas, View v, Paint mPaint) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mLocationSize[0], mLocationSize[1], mLocationSize[0] + w, mLocationSize[1] + h, mPaint);
    }

    private void drawCorner(Canvas canvas, View v, Paint mPaint) {
        float sw = mPaint.getStrokeWidth();
        mPaint.setStrokeWidth(mStrokeW);

        canvas.drawLine(mLocationSize[0], mLocationSize[1], mLocationSize[0] + mCornerW, mLocationSize[1], mPaint);
        canvas.drawLine(mLocationSize[0], mLocationSize[1], mLocationSize[0], mLocationSize[1] + mCornerW, mPaint);

        canvas.drawLine(mLocationSize[0] + w - mCornerW, mLocationSize[1], mLocationSize[0] + w, mLocationSize[1], mPaint);
        canvas.drawLine(mLocationSize[0] + w, mLocationSize[1], mLocationSize[0] + w, mLocationSize[1] + mCornerW, mPaint);

        canvas.drawLine(mLocationSize[0], mLocationSize[1] + h, mLocationSize[0], mLocationSize[1] + h - mCornerW, mPaint);
        canvas.drawLine(mLocationSize[0], mLocationSize[1] + h, mLocationSize[0] + mCornerW, mLocationSize[1] + h, mPaint);

        canvas.drawLine(mLocationSize[0] + w - mCornerW, mLocationSize[1] + h, mLocationSize[0] + w, mLocationSize[1] + h, mPaint);
        canvas.drawLine(mLocationSize[0] + w, mLocationSize[1] + h - mCornerW, mLocationSize[0] + w, mLocationSize[1] + h, mPaint);
        mPaint.setStrokeWidth(sw);

    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_border);
    }
}
