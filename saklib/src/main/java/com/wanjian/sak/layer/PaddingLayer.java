package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingLayer extends LayerAdapter {
    private Rect mRect = new Rect();

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


        if (l != 0) {
            String txt = "PL" + convertSize(l).getLength();
            paint.getTextBounds(txt, 0, txt.length(), mRect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0], locationSize[1] + h / 2, locationSize[0] + mRect.width(), locationSize[1] + h / 2 + mRect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0], locationSize[1] + h / 2 + mRect.height(), paint);
        }
        if (t != 0) {
            String txt = "PT" + convertSize(t).getLength();
            paint.getTextBounds(txt, 0, txt.length(), mRect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1], locationSize[0] + w / 2 + mRect.width(), locationSize[1] + mRect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + mRect.height(), paint);
        }
        if (r != 0) {
            String txt = "PR" + convertSize(r).getLength();
            paint.getTextBounds(txt, 0, txt.length(), mRect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w - mRect.width(), locationSize[1] + h / 2, locationSize[0] + w, locationSize[1] + h / 2 + mRect.height(), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w - mRect.width(), locationSize[1] + h / 2 + mRect.height(), paint);
        }
        if (b != 0) {
            String txt = "PB" + convertSize(b).getLength();
            paint.getTextBounds(txt, 0, txt.length(), mRect);
            paint.setColor(0x88ffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1] + h - mRect.height(), locationSize[0] + w / 2 + mRect.width(), locationSize[1] + h, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + h, paint);
        }
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_padding);
    }
}
