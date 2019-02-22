package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.wanjian.sak.layer.AbsLayer;


/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class LayerAdapter extends AbsLayer {

    public LayerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onUiUpdate(Canvas canvas, View rootView) {
    }


    protected abstract void onDrawLayer(Canvas canvas, View view);
}
