package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class InfoLayer extends LayerTxtAdapter {
    public static final int INFO_KEY = R.layout.sak_container_layout;

    public InfoLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        Object obj = view.getTag(INFO_KEY);
        String info;
        if (obj == null) {
            info = "";
        } else {
            info = obj.toString();
        }
        return info;

    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_custom_info_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_personal_info);
    }
}
