package com.wanjian.sak.layer.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;

/**
 * Created by wanjian on 2017/3/9.
 */

public class ViewClassLayer extends LayerTxtAdapter {


    @Override
    protected String getTxt(View view) {
        return view.getClass().getSimpleName();
    }

}
