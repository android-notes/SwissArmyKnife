package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;


import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by wanjian on 2016/10/26.
 */

public class BackgroundColorCanvas extends CanvasLayerTxtAdapter {
    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        if (SDK_INT < 11) {
            return;
        }
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            String txt = String.format("#%08x", color);
            drawTxt(txt, canvas, paint, view);
        }

    }
}
