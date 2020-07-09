package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.ScreenUtils;


/**
 * Created by wanjian on 2016/10/24.
 */

public class BorderLayer extends ViewLayer {

  private Paint mBorderPaint;
  private Paint mCornerPaint;
  private int mCornerW;

  private int w;
  private int h;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBorderPaint.setStyle(Paint.Style.STROKE);
    mBorderPaint.setColor(getBorderColor());

    mCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mCornerPaint.setStyle(Paint.Style.STROKE);
    mCornerPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(), 1));
    mCornerPaint.setColor(getCornerColor());

    mCornerW = ScreenUtils.dp2px(getContext(), 6);
//    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas, View view) {
    w = view.getWidth();
    h = view.getHeight();
    drawBorder(canvas);
    drawCorner(canvas);
  }

  protected int getBorderColor() {
    return getContext().getResources().getColor(R.color.sak_color_primary);
  }

  protected int getCornerColor() {
    return Color.MAGENTA;
  }

  private void drawBorder(Canvas canvas) {
    canvas.drawRect(0, 0, w, h, mBorderPaint);
  }

  private void drawCorner(Canvas canvas) {
    //--
    canvas.drawLine(0, 0, mCornerW, 0, mCornerPaint);
    //|
    canvas.drawLine(0, 0, 0, mCornerW, mCornerPaint);
    //  --
    canvas.drawLine(w - mCornerW, 0, w, 0, mCornerPaint);
    //    |
    canvas.drawLine(w, 0, w, mCornerW, mCornerPaint);
    //
    canvas.drawLine(0, h, 0, h - mCornerW, mCornerPaint);
    canvas.drawLine(0, h, mCornerW, h, mCornerPaint);

    canvas.drawLine(w - mCornerW, h, w, h, mCornerPaint);
    canvas.drawLine(w, h - mCornerW, w, h, mCornerPaint);

  }

}
