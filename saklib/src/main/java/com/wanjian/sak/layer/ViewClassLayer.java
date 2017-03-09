package com.wanjian.sak.layer;

import android.content.Context;
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
    public String description() {
        return mContext.getString(R.string.sak_view_name);
    }
}
