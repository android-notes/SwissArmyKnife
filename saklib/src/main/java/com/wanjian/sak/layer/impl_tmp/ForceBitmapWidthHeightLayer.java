package com.wanjian.sak.layer.impl_tmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;

import java.lang.reflect.Field;

/**
 * Created by wanjian on 2016/10/25.
 */
@Deprecated
public class ForceBitmapWidthHeightLayer extends LayerTxtAdapter {
    public ForceBitmapWidthHeightLayer(Context context) {
        super(context);
    }


    @Override
    protected String getTxt(View view) {
        StringBuilder builder = new StringBuilder(10);
        Class<?> clz = view.getClass();
        findBmp(clz, view, builder);
        if (builder.length() > 0) {
            return builder.toString();
        }

        return "";
    }

    private void findBmp(Class<?> clz, View view, StringBuilder builder) {
        if (clz == Object.class || clz == null) {
            return;
        }

        Field[] fs = clz.getDeclaredFields();
        ISizeConverter converter = getSizeConverter();
        Context context = getContext();

        for (Field field : fs) {
            if (field.getType() == Bitmap.class) {
                field.setAccessible(true);
                try {
                    Bitmap bitmap = ((Bitmap) field.get(view));

                    builder.append((int) converter.convert(context, bitmap.getWidth()).getLength()).append("-").append((int) converter.convert(context, bitmap.getHeight()).getLength()).append(" ");
                } catch (Exception e) {
                }
                field.setAccessible(false);
            }
        }

        findBmp(clz.getSuperclass(), view, builder);

    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_force_image_w_h);
    }

    @Override
    public Drawable icon() {
        return null;
    }
}
