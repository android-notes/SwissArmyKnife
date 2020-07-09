package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.wanjian.sak.utils.ScreenUtils;

/**
 * Created by wanjian on 2016/10/26.
 * <p>
 * 在view左上角画浅白色背景和文本
 */

public abstract class LayerTxtAdapter extends ViewLayer {
  private Paint mPaint;
  private Rect mRect = new Rect();

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setTextSize(ScreenUtils.dp2px(getContext(), 10));
    mPaint.setColor(getColor());
  }

  @Override
  protected void onDraw(Canvas canvas, View view) {
    String txt = getTxt(view);
    if (txt == null) {
      txt = "";
    }
    drawTxt(txt, canvas, view);
  }

  protected abstract String getTxt(View view);

  private void drawTxt(String txt, Canvas canvas, View view) {
    mPaint.setTextSize(getTextSize());
    mPaint.getTextBounds(txt, 0, txt.length(), mRect);
    mPaint.setColor(getBackgroundColor());
    canvas.drawRect(0, 0, mRect.width(), mRect.height(), mPaint);
    mPaint.setColor(getColor());
    canvas.drawText(txt, 0, mRect.height(), mPaint);
  }

  protected int getColor() {
    return Color.BLACK;
  }

  protected int getBackgroundColor() {
    return 0x88ffffff;
  }

  public float getTextSize() {
    return ScreenUtils.dp2px(getContext(), 10);
  }
}
