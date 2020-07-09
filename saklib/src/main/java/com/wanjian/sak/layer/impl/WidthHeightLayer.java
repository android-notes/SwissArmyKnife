package com.wanjian.sak.layer.impl;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;

/**
 *
 */

public class WidthHeightLayer extends LayerTxtAdapter implements ISize {


  @Override
  protected String getTxt(View view) {
    int w = view.getWidth();
    int h = view.getHeight();
    ISizeConverter converter = getSizeConverter();
    return converter.convert(getContext(), w).getLength() + ":" + converter.convert(getContext(), h).getLength();
  }


  private ISizeConverter getSizeConverter() {
    return sizeConverter == null ? ISizeConverter.CONVERTER : sizeConverter;
  }

  private ISizeConverter sizeConverter;

  @Override
  public void onSizeConvertChange(ISizeConverter converter) {
    sizeConverter = converter;
    invalidate();
  }

}
