package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class MarginLayer extends LayerAdapter {

    private Rect mRect = new Rect();

    public MarginLayer(Context context) {
        super(context);
    }

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {//画 外边距
            int[] locationSize = getLocationAndSize(view);
            int w = view.getWidth();
            int h = view.getHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0x33ff0000);
            int l = locationSize[0] - marginLayoutParams.leftMargin;
            int t = locationSize[1] - marginLayoutParams.topMargin;
            int r = locationSize[0] + w + marginLayoutParams.rightMargin;
            int b = locationSize[1] + h + marginLayoutParams.bottomMargin;

            canvas.drawRect(l, locationSize[1], locationSize[0], locationSize[1] + h, paint);//left
            canvas.drawRect(locationSize[0] + w, locationSize[1], r, locationSize[1] + h, paint);//right
            canvas.drawRect(locationSize[0], t, locationSize[0] + w, locationSize[1], paint);
            canvas.drawRect(locationSize[0], locationSize[1] + h, locationSize[0] + w, b, paint);


            if (marginLayoutParams.leftMargin != 0) {
                String txt = "ML" + convertSize(marginLayoutParams.leftMargin).getLength();
                paint.getTextBounds(txt, 0, txt.length(), mRect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(l, locationSize[1] + locationSize[3] / 2, l + mRect.width(), locationSize[1] + locationSize[3] / 2 + mRect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, l, locationSize[1] + locationSize[3] / 2 + mRect.height(), paint);
            }
            if (marginLayoutParams.topMargin != 0) {
                String txt = "MT" + convertSize(marginLayoutParams.topMargin).getLength();
                paint.getTextBounds(txt, 0, txt.length(), mRect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locationSize[0] + locationSize[2] / 2, t, locationSize[0] + locationSize[2] / 2 + mRect.width(), t + mRect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, locationSize[0] + locationSize[2] / 2, t + mRect.height(), paint);
            }

            if (marginLayoutParams.rightMargin != 0) {
                String txt = "MR" + convertSize(marginLayoutParams.rightMargin).getLength();
                paint.getTextBounds(txt, 0, txt.length(), mRect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(r - mRect.width(), locationSize[1] + locationSize[3] / 2, r, locationSize[1] + locationSize[3] / 2 + mRect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, r - mRect.width(), locationSize[1] + locationSize[3] / 2 + mRect.height(), paint);
            }

            if (marginLayoutParams.bottomMargin != 0) {
                String txt = "MB" + convertSize(marginLayoutParams.bottomMargin).getLength();
                paint.getTextBounds(txt, 0, txt.length(), mRect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locationSize[0] + locationSize[2] / 2, b - mRect.height(), locationSize[0] + locationSize[2] / 2 + mRect.width(), b, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, locationSize[0] + locationSize[2] / 2, b, paint);
            }


        }
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_margin);
    }
}
