package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by wanjian on 2016/10/26.
 */

public class BackgroundColorLayer extends LayerTxtAdapter {


    public BackgroundColorLayer(Context context) {
        super(context);
    }

    @Override
    protected String getTxt(View view) {
        if (SDK_INT < 11) {
            return "";
        }
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = drawable.getCurrent();
        }
        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            String txt = String.format("#%08x", color);
            return txt;
        }

        return "";
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_bag_color);
    }
}
