package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;

public class TakeColorLayer extends Layer {
  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private int x;
  private int y;
  private int r;
  private boolean consume;
  private float lastX, lastY;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    x = getRootView().getWidth() / 2;
    y = getRootView().getHeight() / 2;
    r = ScreenUtils.dp2px(getContext(), 60);
    paint.setStyle(Paint.Style.STROKE);
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
      if (contains(lastX, lastY)) {
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
    x += dx;

    y += dy;
    invalidate();
    lastX = curX;
    lastY = curY;
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.translate(x, y);
    paint.setColor(Color.BLACK);
    paint.setStrokeWidth(0);
    canvas.drawLine(-r, 0, r, 0, paint);
    canvas.drawLine(0, -r, 0, r, paint);

    paint.setStrokeWidth(ScreenUtils.dp2px(getContext(), 10));
    paint.setColor(Color.RED);

    canvas.drawCircle(0, 0, r, paint);
  }

  private boolean contains(float lastX, float lastY) {
    double d = Math.sqrt(Math.pow((lastX - x), 2) + Math.pow((lastY - y), 2));
    return d < r;
  }

}
