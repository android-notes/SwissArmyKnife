package com.wanjian.sak.layer.impl;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/10/26.
 */

public class TextColorLayer extends LayerTxtAdapter {


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


}
