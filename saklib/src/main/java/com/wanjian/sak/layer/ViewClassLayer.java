package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;

/**
 * Created by wanjian on 2017/3/9.
 */

public class ViewClassLayer extends LayerTxtAdapter {

    public ViewClassLayer(Context context) {
        super(context);
    }

    @Override
    protected String getTxt(View view) {
        return view.getClass().getSimpleName();
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_controller_type_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_view_name);
    }
}
