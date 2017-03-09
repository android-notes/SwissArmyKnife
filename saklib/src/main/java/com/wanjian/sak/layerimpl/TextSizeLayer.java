package com.wanjian.sak.layerimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.layerimpl.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextSizeLayer extends LayerTxtAdapter {

    public TextSizeLayer(Context context) {
        super(context);
    }

    @Override
    protected String getTxt(View view) {
        if (view instanceof TextView) {
            float size = ((TextView) view).getTextSize();
            return String.valueOf(convertSize(size).getLength());
        }

        return "";
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_txt_size);
    }
}
