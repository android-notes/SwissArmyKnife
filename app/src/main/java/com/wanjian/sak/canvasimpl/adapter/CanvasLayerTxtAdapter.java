package com.wanjian.sak.canvasimpl.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by wanjian on 2016/10/26.
 * <p>
 * 在view左上角画浅白色背景和文本
 */

public abstract class CanvasLayerTxtAdapter extends CanvasLayerAdapter {
    protected void drawTxt(String txt, Canvas canvas, Paint paint, View view) {
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        Rect rect = new Rect();
        paint.getTextBounds(txt, 0, txt.length(), rect);
        paint.setColor(0x88ffffff);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(locations[0], locations[1], locations[0] + rect.width() + 2, locations[1] + rect.height() + 2, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText(txt, locations[0] + 1, locations[1] + 1 + rect.height(), paint);
    }
}
