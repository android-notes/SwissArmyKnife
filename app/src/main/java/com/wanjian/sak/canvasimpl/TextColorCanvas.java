package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextColorCanvas extends CanvasLayerTxtAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        if (view instanceof TextView) {
            int color = ((TextView) view).getCurrentTextColor();
            String txt = String.format("#%08x", color);
            drawTxt(txt, canvas, paint, view);
        }

    }
}
