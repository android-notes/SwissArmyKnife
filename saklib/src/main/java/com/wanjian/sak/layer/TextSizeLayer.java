package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


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
            return String.valueOf(getSizeConverter().convert(getContext(), size).getLength());
        }

        return "";
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_text_size_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_txt_size);
    }
}
