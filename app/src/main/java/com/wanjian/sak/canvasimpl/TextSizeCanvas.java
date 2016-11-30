package com.wanjian.sak.canvasimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextSizeCanvas extends CanvasLayerTxtAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        if (view instanceof TextView) {
            float size = ((TextView) view).getTextSize();
            String txt = px2sp(view.getContext(), size) + "sp/ " + px2dp(view.getContext(), size) + "dp";
            drawTxt(txt, canvas, paint, view);
        }

    }
}
