package com.wanjian.sak.layer.impl;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextSizeLayer extends LayerTxtAdapter implements ISize {
  @Override
  protected String getTxt(View view) {
    if (view instanceof TextView) {
      float size = ((TextView) view).getTextSize();
      return String.valueOf(getSizeConverter().convert(getContext(), size).getLength());
    }

    return "";
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
