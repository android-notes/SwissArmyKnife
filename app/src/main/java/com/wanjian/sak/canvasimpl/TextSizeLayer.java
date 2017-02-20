package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.canvasimpl.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextSizeLayer extends LayerTxtAdapter {

    public TextSizeLayer(Context context) {
        super(context);
    }

    @Override
    protected String getTxt(View view) {
        if (view instanceof TextView) {
            float size = ((TextView) view).getTextSize();
            String txt = px2sp(size) + "sp/ " + px2dp(size) + "dp";
            return txt;
        }

        return "";
    }

    @Override
    protected String description() {
        return "字体大小";
    }
}
