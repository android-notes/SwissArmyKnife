package com.wanjian.sak.layer.impl;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by wanjian on 2016/10/26.
 */

public class BackgroundColorLayer extends LayerTxtAdapter {

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
    } else if (drawable instanceof ShapeDrawable) {
    }

    return "";
  }

}
