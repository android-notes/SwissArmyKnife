package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by wanjian on 2016/10/26.
 * <p>
 * 在view左上角画浅白色背景和文本
 */

public abstract class LayerTxtAdapter extends LayerAdapter {
    private Rect mRect = new Rect();

    public LayerTxtAdapter(Context context) {
        super(context);
    }


    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        String txt = getTxt(view);
        if (txt == null) {
            txt = "";
        }
        drawTxt(txt, canvas, paint, view);
    }

    protected abstract String getTxt(View view);

    private void drawTxt(String txt, Canvas canvas, Paint paint, View view) {
        paint.setTextSize(getTextSize());
        int[] locationSize = getLocationAndSize(view);

        paint.getTextBounds(txt, 0, txt.length(), mRect);
        paint.setColor(getBackgroundColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(locationSize[0], locationSize[1], locationSize[0] + mRect.width() + 2, locationSize[1] + mRect.height() + 2, paint);
        paint.setColor(getColor());
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText(txt, locationSize[0] + 1, locationSize[1] + 1 + mRect.height(), paint);
    }

    @Override
    protected int getColor() {
        return super.getColor();
    }

    protected int getBackgroundColor() {
        return 0x88ffffff;
    }

    public float getTextSize() {
        return dp2px(10);
    }
}
