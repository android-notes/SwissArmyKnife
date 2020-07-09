package com.wanjian.sak.utils;

import android.content.Context;

public class ScreenUtils {

  public static int dp2px(Context context, int dip) {
    float density = context.getResources().getDisplayMetrics().density;
    return (int) (dip * density + 0.5);
  }

  public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

}
