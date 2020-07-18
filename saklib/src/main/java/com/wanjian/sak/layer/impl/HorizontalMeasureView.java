package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;


/**
 * Created by wanjian on 2016/11/10.
 */

public class HorizontalMeasureView extends Layer implements ISize {
  protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  protected int m20DP;
  protected int maxHeight;
  protected int minHeight;
  private int height;
  private boolean consume;
  private float lastX, lastY;
  private Rect rect = new Rect();

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    init();
  }


  private void init() {
    mPaint.setColor(Color.BLACK);
    m20DP = ScreenUtils.dp2px(getContext(), 20);
    mPaint.setTextSize(ScreenUtils.dp2px(getContext(), 8));
    maxHeight = ScreenUtils.dp2px(getContext(), 10);
    minHeight = ScreenUtils.dp2px(getContext(), 5);
    height = ScreenUtils.dp2px(getContext(), 60);
    rect.left = 0;
    rect.top = (int) (getRootView().getHeight() / 2f - height / 2f);
    rect.right = getRootView().getWidth();
    rect.bottom = (int) (getRootView().getHeight() / 2f + height / 2f);
    invalidate();
  }

  @Override
  protected void onAfterTraversal(View rootView) {
    super.onAfterTraversal(rootView);
    invalidate();
  }

  @Override
  protected boolean onBeforeInputEvent(View rootView, InputEvent event) {
    if (!(event instanceof MotionEvent)) {
      return false;
    }
    MotionEvent motionEvent = ((MotionEvent) event);
    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
      lastX = motionEvent.getX();
      lastY = motionEvent.getY();
      if (rect.contains((int) lastX, (int) lastY)) {
        consume = true;
      } else {
        consume = false;
      }
    }
    if (consume == false) {
      return false;
    }
    float curX = motionEvent.getX();
    float curY = motionEvent.getY();

    int dx = (int) (curX - lastX);
    int dy = (int) (curY - lastY);
    rect.left += dx;
    rect.right += dx;
    rect.top += dy;
    rect.bottom += dy;
    invalidate();
    lastX = curX;
    lastY = curY;
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.translate(rect.left, rect.top);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setColor(0xbbFFFFFF);
    canvas.drawRect(0, 0, rect.right - rect.left, rect.bottom - rect.top, mPaint);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setColor(Color.BLACK);
    canvas.drawRect(0, 0, rect.right - rect.left, rect.bottom - rect.top, mPaint);
    mPaint.setStyle(Paint.Style.FILL);
    int w = rect.right - rect.left;
    canvas.drawLine(0, 0, w, 0, mPaint);
    for (int i = 0; i <= w; i += m20DP) {
      canvas.drawLine(i, -maxHeight, i, maxHeight , mPaint);
      canvas.rotate(90, i, maxHeight);
      canvas.drawText(sizeConverter.convert(getContext(), i).toString(), i, maxHeight, mPaint);
      canvas.rotate(-90, i, maxHeight);
      for (int j = i; j <= w && j < i + m20DP; j += m20DP / 10) {
        canvas.drawLine(j, -minHeight, j, minHeight, mPaint);
      }
    }
  }

  ISizeConverter sizeConverter = ISizeConverter.CONVERTER;

  @Override
  public void onSizeConvertChange(ISizeConverter converter) {
    sizeConverter = converter;
    invalidate();
  }
}
