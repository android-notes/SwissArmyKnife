package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.Size;
import com.wanjian.sak.layer.ISize;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;

import static android.view.View.VISIBLE;

public class RelativeLayerView extends Layer implements ISize {
  private int mTxtSize;
  private Paint mPaint;
  private int[] mLocation1 = new int[2];
  private int[] mLocation2 = new int[2];
  private View mFirstView;
  private View mSecondView;
  private View mTargetView;
  private int mLocation[] = new int[2];
  GestureDetectorCompat detectorCompat;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTxtSize = ScreenUtils.dp2px(getContext(), 10);
    mPaint.setTextSize(mTxtSize);
    init();
  }

  private void init() {
    detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
      @Override
      public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
          System.out.println("长按了。。。。。。");
        View mTargetView = findPressView((int) e.getRawX(), (int) e.getRawY());
        if (mFirstView != null && mSecondView != null) {
          mFirstView = mSecondView;
          mSecondView = null;
        }
        if (mFirstView == null) {
          mFirstView = mTargetView;
        } else {
          mSecondView = mTargetView;
        }
        invalidate();
      }
    });

  }

    @Override
    protected void onAfterTraversal(View rootView) {
        super.onAfterTraversal(rootView);
        invalidate();
    }

    @Override
  protected boolean onBeforeInputEvent(View rootView, InputEvent event) {
    if (event instanceof MotionEvent) {
        System.out.println("手动到事件。。。");
      detectorCompat.onTouchEvent((MotionEvent) event);
    }
    return false;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mFirstView != null) {
      drawBorder(canvas, mFirstView, mLocation1);
    }
    if (mFirstView == mSecondView) {
      return;
    }
    if (mSecondView == null) {
      return;
    }
    drawBorder(canvas, mSecondView, mLocation2);

    mPaint.setStyle(Paint.Style.FILL);
    ISizeConverter sizeConverter = getSizeConverter();
    //2 在 1 左边
    if (mLocation2[0] + mSecondView.getWidth() < mLocation1[0]) {
      canvas.save();
      int x = mLocation2[0] + mSecondView.getWidth();
      int y = mLocation2[1] + mSecondView.getHeight() / 2;
      int length = mLocation1[0] - (mLocation2[0] + mSecondView.getWidth());
      canvas.translate(x, y);
      mPaint.setColor(Color.RED);
      canvas.drawLine(0, 0, length, 0, mPaint);
      Size size = sizeConverter.convert(getContext(), length);
      String txt = String.valueOf(size.getLength());
      float txtLength = mPaint.measureText(txt);
      canvas.translate(length / 2 - txtLength / 2, mTxtSize / 2);
      mPaint.setColor(Color.WHITE);
      canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
      mPaint.setColor(Color.RED);
      canvas.drawText(txt, 0, 0, mPaint);
      canvas.restore();
    }
    //2 在 1 右边
    if (mLocation2[0] > mLocation1[0] + mFirstView.getWidth()) {
      canvas.save();
      int x = mLocation2[0];
      int y = mLocation2[1] + mSecondView.getHeight() / 2;
      int length = mLocation2[0] - (mLocation1[0] + mFirstView.getWidth());
      canvas.translate(x, y);
      mPaint.setColor(Color.RED);
      canvas.drawLine(0, 0, -length, 0, mPaint);
      Size size = sizeConverter.convert(getContext(), length);
      String txt = String.valueOf(size.getLength());
      float txtLength = mPaint.measureText(txt);
      canvas.translate(-length / 2 - txtLength / 2, mTxtSize / 2);
      mPaint.setColor(Color.WHITE);
      canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
      mPaint.setColor(Color.RED);
      canvas.drawText(txt, 0, 0, mPaint);
      canvas.restore();
    }
    //2 在 1 上边
    if (mLocation2[1] + mSecondView.getHeight() < mLocation1[1]) {
      canvas.save();
      int x = mLocation2[0] + mSecondView.getWidth() / 2;
      int y = mLocation2[1] + mSecondView.getHeight();
      int length = mLocation1[1] - (mLocation2[1] + mSecondView.getHeight());
      canvas.translate(x, y);
      mPaint.setColor(Color.RED);
      canvas.drawLine(0, 0, 0, length, mPaint);
      Size size = sizeConverter.convert(getContext(), length);
      String txt = String.valueOf(size.getLength());
      float txtLength = mPaint.measureText(txt);
      canvas.translate(-txtLength / 2, length / 2 + mTxtSize / 2);
      mPaint.setColor(Color.WHITE);
      canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
      mPaint.setColor(Color.RED);
      canvas.drawText(txt, 0, 0, mPaint);
      canvas.restore();
    }
    //2 在 1 下边
    if (mLocation2[1] > mLocation1[1] + mFirstView.getHeight()) {
      canvas.save();
      int x = mLocation2[0] + mSecondView.getWidth() / 2;
      int y = mLocation2[1];
      int length = mLocation2[1] - (mLocation1[1] + mFirstView.getHeight());
      canvas.translate(x, y);
      mPaint.setColor(Color.RED);
      canvas.drawLine(0, 0, 0, -length, mPaint);
      Size size = sizeConverter.convert(getContext(), length);
      String txt = String.valueOf(size.getLength());
      float txtLength = mPaint.measureText(txt);
      canvas.translate(-txtLength / 2, -length / 2 + mTxtSize / 2);
      mPaint.setColor(Color.WHITE);
      canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
      mPaint.setColor(Color.RED);
      canvas.drawText(txt, 0, 0, mPaint);
      canvas.restore();
    }

  }

  private void drawBorder(Canvas canvas, View view, int[] mLocation) {
    view.getLocationInWindow(mLocation);
    canvas.save();
    mPaint.setColor(Color.RED);
    mPaint.setStyle(Paint.Style.STROKE);
    canvas.drawRect(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight(), mPaint);
    canvas.restore();
  }


  protected View findPressView(int x, int y) {
    mTargetView = getRootView();
    traversal(mTargetView, x, y);
    return mTargetView;
  }

  private void traversal(View view, int x, int y) {

    if (view.getVisibility() != VISIBLE) {
      return;

    }
    if (inRange(view, x, y) == false) {
      return;
    }

    mTargetView = view;
    if (view instanceof ViewGroup) {
      ViewGroup viewGroup = ((ViewGroup) view);
      for (int i = 0; i < viewGroup.getChildCount(); i++) {
        View child = viewGroup.getChildAt(i);
        traversal(child, x, y);
      }
    }
  }

  private boolean inRange(View view, int x, int y) {
    view.getLocationOnScreen(mLocation);

    return (mLocation[0] <= x
        && mLocation[1] <= y
        && mLocation[0] + view.getWidth() >= x
        && mLocation[1] + view.getHeight() >= y);
  }


  public void onDetached(View view) {
    clean();
  }

  private void clean() {
    mTargetView = mFirstView = mSecondView = null;
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
