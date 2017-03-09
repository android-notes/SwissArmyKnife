package com.wanjian.sak.layerimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.layerimpl.adapter.LayerTxtAdapter;


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
    public String description() {
        return mContext.getString(R.string.sak_txt_color);
    }
}
