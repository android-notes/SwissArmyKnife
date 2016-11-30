package com.wanjian.sak.canvasimpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/25.
 */

public class BitmapWidthHeightCanvas extends CanvasLayerTxtAdapter {
    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        if (view instanceof ImageView) {
            drawWH(canvas, paint, ((ImageView) view));
        }
    }

    private void drawWH(Canvas canvas, Paint paint, ImageView view) {
        if (view == null) {
            return;
        }
        Drawable drawable = view.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                String txt = "img w:" + bmp.getWidth() + " h:" + bmp.getHeight();
                drawTxt(txt, canvas, paint, view);
            }
        }

    }
}
