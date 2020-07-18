package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;

public class GridLayer extends Layer {

  private Paint mPaint;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(color());
    invalidate();
  }

  @Override
  protected void onEnableChange(boolean enable) {
    super.onEnableChange(enable);

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int w = getRootView().getWidth();
    int h = getRootView().getHeight();
    int space = space();

    for (int i = space; i < w; i += space) {
      canvas.drawLine(i, 0, i, h, mPaint);
    }
    for (int i = space; i < h; i += space) {
      canvas.drawLine(0, i, w, i, mPaint);
    }
  }


  @Override
  protected void onAfterTraversal(View rootView) {
    invalidate();
  }

  protected int color() {
    return 0x55000000;
  }

  protected int space() {
    return ScreenUtils.dp2px(getContext(), 5);
  }


}
