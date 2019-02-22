package com.wanjian.sak.layer.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by wanjian on 2016/10/26.
 * <p>
 * 在view左上角画浅白色背景和文本
 */

public abstract class LayerTxtAdapter extends LayerAdapter {

    public LayerTxtAdapter(Context context) {
        super(context);
    }


    @Override
    protected void onDrawLayer(Canvas canvas, View view) {

    }

    protected abstract String getTxt(View view);


    protected int getColor() {
        return 0;
    }

    protected int getBackgroundColor() {
        return 0;
    }

    public float getTextSize() {
        return 0;
    }
}
