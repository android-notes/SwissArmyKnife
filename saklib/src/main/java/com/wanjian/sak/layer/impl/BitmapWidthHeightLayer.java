package com.wanjian.sak.layer.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;


/**
 *
 */

public class BitmapWidthHeightLayer extends LayerTxtAdapter implements ISize {
  private ISizeConverter sizeConverter = ISizeConverter.CONVERTER;

  @Override
  protected String getTxt(View view) {
    ISizeConverter converter = sizeConverter;
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


  @Override
  public void onSizeConvertChange(ISizeConverter converter) {
    sizeConverter = converter;
    invalidate();
  }
}
