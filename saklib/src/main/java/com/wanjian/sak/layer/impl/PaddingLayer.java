package com.wanjian.sak.layer.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;
import com.wanjian.sak.utils.ScreenUtils;

import java.text.DecimalFormat;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingLayer extends ViewLayer implements ISize {
  private Paint mTextPaint;
  private Paint mBgPaint;
  private Paint mPaddingPaint;
  private int mTxtColor = 0xFF000000;
  private int mBgColor = 0x88FFFFFF;
  private int mPaddingColor = 0x3300FF0D;
  private Rect mRect = new Rect();
  private DecimalFormat mFormat = new DecimalFormat("#.###");

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setTextSize(ScreenUtils.dp2px(getContext(), 10));
    mTextPaint.setColor(mTxtColor);

    mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBgPaint.setColor(mBgColor);

    mPaddingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaddingPaint.setColor(mPaddingColor);
  }

  protected void onDraw(Canvas canvas, View view) {
    int l = view.getPaddingLeft();
    int t = view.getPaddingTop();
    int r = view.getPaddingRight();
    int b = view.getPaddingBottom();
    int w = view.getWidth();
    int h = view.getHeight();

    canvas.drawRect(0, 0, l, h, mPaddingPaint);
    canvas.drawRect(0, 0, w, t, mPaddingPaint);
    canvas.drawRect(w - r, 0, w, h, mPaddingPaint);
    canvas.drawRect(0, h - b, w, h, mPaddingPaint);

    ISizeConverter converter = getSizeConverter();
    Context context = getContext();
    if (l != 0) {
      String txt = "L" + mFormat.format(converter.convert(context, l).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(0, h / 2f - mRect.height() / 2f, mRect.width(), h / 2f + mRect.height() / 2f, mBgPaint);
      canvas.drawText(txt, 0, h / 2f + mRect.height() / 2f, mTextPaint);
    }
    if (t != 0) {
      String txt = "T" + mFormat.format(converter.convert(context, t).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(w / 2f - mRect.width() / 2f, 0, w / 2f + mRect.width() / 2f, mRect.height(), mBgPaint);
      canvas.drawText(txt, w / 2f - mRect.width() / 2f, mRect.height(), mTextPaint);
    }
    if (r != 0) {
      String txt = "R" + mFormat.format(converter.convert(context, r).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(w - mRect.width(), h / 2f - mRect.height() / 2f, w, h / 2f + mRect.height() / 2f, mBgPaint);
      canvas.drawText(txt, w - mRect.width(), h / 2f + mRect.height() / 2f, mTextPaint);
    }
    if (b != 0) {
      String txt = "B" + mFormat.format(converter.convert(context, b).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(w / 2f - mRect.width() / 2f, h - mRect.height(), w / 2f + mRect.width() / 2f, h, mBgPaint);
      canvas.drawText(txt, w / 2f - mRect.width() / 2f, h, mTextPaint);
    }
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
