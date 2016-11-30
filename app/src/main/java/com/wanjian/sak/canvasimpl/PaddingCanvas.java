package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingCanvas extends CanvasLayerAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        int w = view.getWidth();
        int h = view.getHeight();
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x3300ff00);
        canvas.drawRect(locations[0], locations[1], locations[0] + l, locations[1] + h, paint);
        canvas.drawRect(locations[0], locations[1], locations[0] + w, locations[1] + t, paint);
        canvas.drawRect(locations[0] + w - r, locations[1], locations[0] + w, locations[1] + h, paint);
        canvas.drawRect(locations[0], locations[1] + h - b, locations[0] + w, locations[1] + h, paint);


        Rect rect = new Rect();
        if (l != 0) {
            String txt = "PL" + px2dp(view.getContext(), l);
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locations[0], locations[1] + h / 2, locations[0] + rect.width(), locations[1] + h / 2 + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locations[0], locations[1] + h / 2 + rect.height(), paint);
        }
        if (t != 0) {
            String txt = "PT" + px2dp(view.getContext(), t);
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locations[0] + w / 2, locations[1], locations[0] + w / 2 + rect.width(), locations[1] + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locations[0] + w / 2, locations[1] + rect.height(), paint);
        }
        if (r != 0) {
            String txt = "PR" + px2dp(view.getContext(), r);
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locations[0] + w - rect.width(), locations[1] + h / 2, locations[0] + w, locations[1] + h / 2 + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locations[0] + w - rect.width(), locations[1] + h / 2 + rect.height(), paint);
        }
        if (b != 0) {
            String txt = "PB" + px2dp(view.getContext(), b);
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locations[0] + w / 2, locations[1] + h - rect.height(), locations[0] + w / 2 + rect.width(), locations[1] + h, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locations[0] + w / 2, locations[1] + h, paint);
        }
    }
}
