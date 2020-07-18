package com.wanjian.sak.layer.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;

import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.BitmapCreater;
import com.wanjian.sak.utils.ScreenUtils;

public class TakeColorLayer extends Layer {
  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private int x;
  private int y;
  private int r;
  private boolean consume;
  private float lastX, lastY;
  private Bitmap bitmap;
  private int strokeWidth;
  private Path path;
  private Matrix matrix = new Matrix();
  private Rect mRect = new Rect();

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    x = getRootView().getWidth() / 2;
    y = getRootView().getHeight() / 2;
    r = ScreenUtils.dp2px(getContext(), 60);
    paint.setStyle(Paint.Style.STROKE);
    strokeWidth = ScreenUtils.dp2px(getContext(), 10);
    path = new Path();
    path.addCircle(0, 0, r, Path.Direction.CCW);
    txtPaint.setStyle(Paint.Style.FILL);
    txtPaint.setTextSize(getTextSize());
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
    drawBitmap();
    invalidate();
    lastX = curX;
    lastY = curY;
    return true;
  }

  private void drawBitmap() {
    View rootView = getRootView();
    int w = rootView.getWidth();
    int h = rootView.getHeight();
    if (bitmap == null || bitmap.getWidth() < w || bitmap.getHeight() < h) {
      bitmap = BitmapCreater.create(w, h, Bitmap.Config.ARGB_8888);
    }
    if (bitmap == null) {
      return;
    }
    Canvas canvas = new Canvas(bitmap);
    rootView.draw(canvas);
  }

  @Override
  protected void onAfterTraversal(View rootView) {
    super.onAfterTraversal(rootView);
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.translate(x, y);
    canvas.clipPath(path);

    if (bitmap != null && x >= 0 && y >= 0 && x < bitmap.getWidth() && y < bitmap.getHeight()) {
      canvas.save();
      canvas.translate(-x, -y);
      matrix.setScale(8, 8, x, y);
      canvas.drawBitmap(bitmap, matrix, paint);
      canvas.restore();

      int color = bitmap.getPixel(x, y);
      paint.setColor(color);
      paint.setStrokeWidth(strokeWidth);
      canvas.drawCircle(0, 0, r - strokeWidth, paint);

      drawText(canvas, color);
    }

    paint.setColor(Color.BLACK);
    paint.setStrokeWidth(0);


    canvas.drawLine(-r, 0, r, 0, paint);
    canvas.drawLine(0, -r, 0, r, paint);
    paint.setStrokeWidth(strokeWidth);
    paint.setColor(Color.RED);
    canvas.drawCircle(0, 0, r, paint);
  }

  private void drawText(Canvas canvas, int color) {
    canvas.save();
    String txt = String.format("#%08x", color).toUpperCase();
    txtPaint.getTextBounds(txt, 0, txt.length(), mRect);

    canvas.translate(-(mRect.right - mRect.left) / 2, -r / 2);

    txtPaint.setColor(color);
    canvas.drawRect(0, 0, mRect.width(), mRect.height(), paint);
    txtPaint.setColor(~color | 0xFF000000);
    canvas.drawText(txt, 0, mRect.height(), txtPaint);

    canvas.restore();
  }

  protected float getTextSize() {
    return ScreenUtils.dp2px(getContext(), 10);
  }

  private boolean contains(float lastX, float lastY) {
    double d = Math.sqrt(Math.pow((lastX - x), 2) + Math.pow((lastY - y), 2));
    return d < r;
  }

}
