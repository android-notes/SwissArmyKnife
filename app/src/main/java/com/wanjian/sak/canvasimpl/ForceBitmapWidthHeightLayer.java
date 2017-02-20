package com.wanjian.sak.canvasimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;


import com.wanjian.sak.canvasimpl.adapter.LayerTxtAdapter;

import java.lang.reflect.Field;

/**
 * Created by wanjian on 2016/10/25.
 */

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

        for (Field field : fs) {
            if (field.getType() == Bitmap.class) {
                field.setAccessible(true);
                try {
                    Bitmap bitmap = ((Bitmap) field.get(view));
                    builder.append(bitmap.getWidth()).append("-").append(bitmap.getHeight()).append(" ");
                } catch (Exception e) {
                }
                field.setAccessible(false);
            }
        }

        findBmp(clz.getSuperclass(), view, builder);

    }

    @Override
    protected String description() {
        return "强制图片宽高";
    }
}
