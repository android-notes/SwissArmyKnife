package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class BorderLayer extends LayerAdapter {

    private final Paint mPaint;
    private int mCornerW;

    private int w;
    private int h;

    public BorderLayer(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mCornerW = dp2px(6);
        mPaint.setStrokeWidth(dp2px(1));
    }


    @Override
    protected void onDrawLayer(Canvas canvas, View view) {
        w = view.getWidth();
        h = view.getHeight();
        mPaint.setColor(getBorderColor());
        drawBorder(canvas);
        mPaint.setColor(getCornerColor());
        drawCorner(canvas);
    }

    protected int getBorderColor() {
        return getContext().getResources().getColor(R.color.sak_color_primary);
    }

    protected int getCornerColor() {
        return Color.MAGENTA;
    }

    private void drawBorder(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, w, h, mPaint);
    }

    private void drawCorner(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        //--
        canvas.drawLine(0, 0, mCornerW, 0, mPaint);
        //|
        canvas.drawLine(0, 0, 0, mCornerW, mPaint);
        //  --
        canvas.drawLine(w - mCornerW, 0, w, 0, mPaint);
        //    |
        canvas.drawLine(w, 0, w, mCornerW, mPaint);
        //
        canvas.drawLine(0, h, 0, h - mCornerW, mPaint);
        canvas.drawLine(0, h, mCornerW, h, mPaint);

        canvas.drawLine(w - mCornerW, h, w, h, mPaint);
        canvas.drawLine(w, h - mCornerW, w, h, mPaint);

    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_border_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_border);
    }
}
