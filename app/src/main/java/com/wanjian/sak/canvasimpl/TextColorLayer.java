package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.canvasimpl.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextColorLayer extends LayerTxtAdapter {

    public TextColorLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        if (view instanceof TextView) {
            int color = ((TextView) view).getCurrentTextColor();
            String txt = String.format("#%08x", color);
            return txt;
        }
        return "";
    }

    @Override
    protected String description() {
        return "字体颜色";
    }
}
