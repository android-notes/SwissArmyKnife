package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class WidthHeightCanvas extends CanvasLayerTxtAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        int w = view.getWidth();
        int h = view.getHeight();
        String txt = "w:" + px2dp(view.getContext(), w) + " h:" + px2dp(view.getContext(), h);
        drawTxt(txt, canvas, paint, view);
    }
}
