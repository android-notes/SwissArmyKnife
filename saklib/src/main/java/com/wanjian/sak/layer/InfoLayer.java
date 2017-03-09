package com.wanjian.sak.layer;

import android.content.Context;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.SAK;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/24.
 */

public class InfoLayer extends LayerTxtAdapter {
    public InfoLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        Object obj = view.getTag(SAK.INFO_KEY);
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
        return mContext.getString(R.string.sak_personal_info);
    }
}
