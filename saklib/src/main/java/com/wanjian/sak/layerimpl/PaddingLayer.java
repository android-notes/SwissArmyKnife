package com.wanjian.sak.layerimpl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.wanjian.sak.layerimpl.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingLayer extends LayerAdapter {

    public PaddingLayer(Context context) {
        super(context);
    }

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        int w = view.getWidth();
        int h = view.getHeight();
        int[] locationSize = getLocationAndSize(view);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x3300ff00);
        canvas.drawRect(locationSize[0], locationSize[1], locationSize[0] + l, locationSize[1] + h, paint);
        canvas.drawRect(locationSize[0], locationSize[1], locationSize[0] + w, locationSize[1] + t, paint);
        canvas.drawRect(locationSize[0] + w - r, locationSize[1], locationSize[0] + w, locationSize[1] + h, paint);
        canvas.drawRect(locationSize[0], locationSize[1] + h - b, locationSize[0] + w, locationSize[1] + h, paint);


        Rect rect = new Rect();
        if (l != 0) {
            String txt = "PL" + convertSize(l).getLength();
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0], locationSize[1] + h / 2, locationSize[0] + rect.width(), locationSize[1] + h / 2 + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0], locationSize[1] + h / 2 + rect.height(), paint);
        }
        if (t != 0) {
            String txt = "PT" + convertSize(t).getLength();
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1], locationSize[0] + w / 2 + rect.width(), locationSize[1] + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + rect.height(), paint);
        }
        if (r != 0) {
            String txt = "PR" + convertSize(r).getLength();
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w - rect.width(), locationSize[1] + h / 2, locationSize[0] + w, locationSize[1] + h / 2 + rect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w - rect.width(), locationSize[1] + h / 2 + rect.height(), paint);
        }
        if (b != 0) {
            String txt = "PB" + convertSize(b).getLength();
            paint.getTextBounds(txt, 0, txt.length(), rect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1] + h - rect.height(), locationSize[0] + w / 2 + rect.width(), locationSize[1] + h, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + h, paint);
        }
    }

    @Override
    public String description() {
        return "内边距";
    }
}
