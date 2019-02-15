package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


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
        String txt = convertSize(w).getLength() + ":" + convertSize(h).getLength();

        return txt;
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_width_height_icon);
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_width_height);
    }
}
