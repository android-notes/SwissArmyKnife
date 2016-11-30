package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wanjian.sak.CanvasManager;
import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class InfoCanvas extends CanvasLayerTxtAdapter {
    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        Object obj = view.getTag(CanvasManager.INFO_KEY);
        String info;
        if (obj == null) {
            info = "null";
        } else {
            info = obj.toString();
        }
        drawTxt(info, canvas, paint, view);
    }
}
