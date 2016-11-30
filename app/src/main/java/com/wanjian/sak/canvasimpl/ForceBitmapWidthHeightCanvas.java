package com.wanjian.sak.canvasimpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;

import java.lang.reflect.Field;

/**
 * Created by wanjian on 2016/10/25.
 */

public class ForceBitmapWidthHeightCanvas extends CanvasLayerTxtAdapter {
    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        StringBuilder builder = new StringBuilder(10);
        Class<?> clz = view.getClass();
        findBmp(clz, view, builder);
        if (builder.length() > 0) {
            drawTxt(builder.toString(), canvas, paint, view);
        }

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

}
