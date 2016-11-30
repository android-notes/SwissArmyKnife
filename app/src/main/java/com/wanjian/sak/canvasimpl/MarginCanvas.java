package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class MarginCanvas extends CanvasLayerAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {//画 外边距
            int[] locations = new int[2];
            int w = view.getWidth();
            int h = view.getHeight();
            view.getLocationOnScreen(locations);
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0x33ff0000);
            int l = locations[0] - marginLayoutParams.leftMargin;
            int t = locations[1] - marginLayoutParams.topMargin;
            int r = locations[0] + w + marginLayoutParams.rightMargin;
            int b = locations[1] + h + marginLayoutParams.bottomMargin;

            canvas.drawRect(l, locations[1], locations[0], locations[1] + h, paint);//left
            canvas.drawRect(locations[0] + w, locations[1], r, locations[1] + h, paint);//right
            canvas.drawRect(locations[0], t, locations[0] + w, locations[1], paint);
            canvas.drawRect(locations[0], locations[1] + h, locations[0] + w, b, paint);

            Rect rect = new Rect();
            if (marginLayoutParams.leftMargin != 0) {
                String txt = "ML" + px2dp(view.getContext(), marginLayoutParams.leftMargin);
                paint.getTextBounds(txt, 0, txt.length(), rect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(l, locations[1], l + rect.width(), locations[1] + rect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, l, locations[1] + rect.height(), paint);
            }
            if (marginLayoutParams.topMargin != 0) {
                String txt = "MT" + px2dp(view.getContext(), marginLayoutParams.topMargin);
                paint.getTextBounds(txt, 0, txt.length(), rect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locations[0], t, locations[0] + rect.width(), t + rect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, locations[0], locations[1] - marginLayoutParams.topMargin + rect.height(), paint);
            }

            if (marginLayoutParams.rightMargin != 0) {
                String txt = "MR" + px2dp(view.getContext(), marginLayoutParams.rightMargin);
                paint.getTextBounds(txt, 0, txt.length(), rect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locations[0] + w, locations[1], locations[0] + w + rect.width(), locations[1] + rect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, locations[0] + w, locations[1] + rect.height(), paint);
            }

            if (marginLayoutParams.bottomMargin != 0) {
                String txt = "MB" + px2dp(view.getContext(), marginLayoutParams.bottomMargin);
                paint.getTextBounds(txt, 0, txt.length(), rect);
                paint.setColor(0x88ffffff);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locations[0], locations[1] + h, locations[0] + rect.width(), locations[1] + h + rect.height(), paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(txt, locations[0], locations[1] + h + rect.height(), paint);
            }


        }
    }

}
