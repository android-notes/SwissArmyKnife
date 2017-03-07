package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.view.View;

import com.wanjian.sak.CanvasManager;
import com.wanjian.sak.canvasimpl.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class InfoLayer extends LayerTxtAdapter {
//    public static final int INFO_KEY= R.id. // TODO: 2017/2/20
    public InfoLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        Object obj = view.getTag(CanvasManager.INFO_KEY);
        String info;
        if (obj == null) {
            info = "null";
        } else {
            info = obj.toString();
        }
        return info;

    }

    @Override
    public String description() {
        return "自定义信息";
    }
}
