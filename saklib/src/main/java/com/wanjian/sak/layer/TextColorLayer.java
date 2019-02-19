package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextColorLayer extends LayerTxtAdapter {

    public TextColorLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        if (view instanceof TextView) {
            CharSequence charSequence = ((TextView) view).getText();
            if (charSequence instanceof SpannableString) {
                // TODO: 2019/2/17
            }
            int color = ((TextView) view).getCurrentTextColor();
            return String.format("#%08x", color);

        }
        return "";
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_text_color_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_txt_color);
    }
}
