package com.wanjian.sak.layer.impl_tmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;


/**
 * Created by wanjian on 2016/10/25.
 */

public class BitmapWidthHeightLayer extends LayerTxtAdapter {
    public BitmapWidthHeightLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        ISizeConverter converter = getSizeConverter();
        Context context = getContext();
        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable instanceof BitmapDrawable) {
                Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
                if (bmp != null && !bmp.isRecycled()) {
                    return (int) converter.convert(context, bmp.getWidth()).getLength() + ":" + (int) converter.convert(context, bmp.getHeight()).getLength();
                }
            }
        }
        return "";
    }


}
