package com.wanjian.sak.layer.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;

import static android.view.View.VISIBLE;

import androidx.core.view.GestureDetectorCompat;


public class TranslationLayerView extends Layer implements ISize {
  private int mTxtSize;
  private int[] mLocation = new int[2];
  private Paint mPaint;
  private ISizeConverter mSizeConverter;
  private View mTargetView;
  GestureDetectorCompat detectorCompat;
  boolean longPress;


  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mSizeConverter = ISizeConverter.CONVERTER;
    init(getContext());
    invalidate();

    detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
      @Override
      public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
        longPress = true;
        mTargetView = findPressView((int) e.getRawX(), (int) e.getRawY());

        invalidate();
      }
    });

  }


  private void init(Context context) {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.RED);
    mTxtSize = ScreenUtils.dp2px(getContext(), 10);
    mPaint.setTextSize(mTxtSize);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mTargetView == null) {
      return;
    }
    View parent = (View) mTargetView.getParent();
    parent.getLocationInWindow(mLocation);
//    View rootView = getRootView();
    canvas.translate(mLocation[0], mLocation[1]);
    float x = mTargetView.getX();
    float y = mTargetView.getY();
    int w = mTargetView.getWidth();
    int h = mTargetView.getHeight();

    mPaint.setStyle(Paint.Style.STROKE);
    canvas.drawRect(x, y, x + w, y + h, mPaint);
    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawLine(0, y + h / 2, x, y + h / 2, mPaint);
    canvas.drawLine(x + w / 2, 0, x + w / 2, y, mPaint);
    canvas.drawLine(x + w, y + h / 2, parent.getWidth(), y + h / 2, mPaint);
    canvas.drawLine(x + w / 2, y + h, x + w / 2, parent.getHeight(), mPaint);


    drawLeftTxt(canvas, x, y, h);

    drawTopTxt(canvas, x, y, w);

    drawRightTxt(canvas, parent, x, y, w, h);

    canvas.save();
    String txt = mSizeConverter.convert(getContext(), parent.getHeight() - y - h).toString();
    float txtLength = mPaint.measureText(txt);
    canvas.translate(x + (w - txtLength) / 2, (parent.getHeight() - y - h - mTxtSize) / 2 + y + h);
    mPaint.setColor(Color.WHITE);
    canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
    mPaint.setColor(Color.RED);
    canvas.drawText(txt, 0, 0, mPaint);
    canvas.restore();

  }

  private void drawRightTxt(Canvas canvas, View parent, float x, float y, int w, int h) {
    canvas.save();
    String txt = mSizeConverter.convert(getContext(), parent.getWidth() - x - w).toString();
    float txtLength = mPaint.measureText(txt);
    canvas.translate((parent.getWidth() - x - w - txtLength) / 2 + x + w, y + (h + mTxtSize) / 2);
    mPaint.setColor(Color.WHITE);
    canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
    mPaint.setColor(Color.RED);
    canvas.drawText(txt, 0, 0, mPaint);
    canvas.restore();
  }

  private void drawTopTxt(Canvas canvas, float x, float y, int w) {
    canvas.save();
    String txt = mSizeConverter.convert(getContext(), y).toString();
    float txtLength = mPaint.measureText(txt);
    canvas.translate(x + (w - txtLength) / 2, (y + mTxtSize) / 2);
    mPaint.setColor(Color.WHITE);
    canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
    mPaint.setColor(Color.RED);
    canvas.drawText(txt, 0, 0, mPaint);
    canvas.restore();
  }

  private void drawLeftTxt(Canvas canvas, float x, float y, int h) {
    canvas.save();
    String txt = mSizeConverter.convert(getContext(), x).toString();
    float txtLength = mPaint.measureText(txt);
    canvas.translate((x - txtLength) / 2, y + (h + mTxtSize) / 2);
    mPaint.setColor(Color.WHITE);
    canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
    mPaint.setColor(Color.RED);
    canvas.drawText(txt, 0, 0, mPaint);
    canvas.restore();
  }

  Float downX, downY;

  @Override
  protected boolean onBeforeInputEvent(View rootView, InputEvent event) {
    if ((event instanceof MotionEvent) == false) {
      return false;
    }
    MotionEvent motionEvent = (MotionEvent) event;
    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
      longPress = false;
      downX = downY = null;
    }
    if (longPress == false) {
      detectorCompat.onTouchEvent(motionEvent);
    } else {
      motionEvent.setAction(MotionEvent.ACTION_CANCEL);
      if (mTargetView == null) {
        return false;
      }
      if (downX == null) {
        downX = motionEvent.getRawX();
        downY = motionEvent.getRawY();
      } else {
        float curX = motionEvent.getRawX();
        float curY = motionEvent.getRawY();
        mTargetView.setTranslationX(mTargetView.getTranslationX() + (curX - downX));
        mTargetView.setTranslationY(mTargetView.getTranslationY() + (curY - downY));
        invalidate();
        downX = curX;
        downY = curY;
      }
    }
    return false;
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
      for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
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


  @Override
  public void onSizeConvertChange(ISizeConverter converter) {
    mSizeConverter = converter;
    invalidate();
  }
}
