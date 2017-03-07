package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.view.View;

import com.wanjian.sak.canvasimpl.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class WidthHeightLayer extends LayerTxtAdapter {

    public WidthHeightLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        int w = view.getWidth();
        int h = view.getHeight();
        String txt = "w:" + convertSize(w) + " h:" + convertSize(h);

        return txt;
    }

    @Override
    public String description() {
        return "宽高";
    }
}
