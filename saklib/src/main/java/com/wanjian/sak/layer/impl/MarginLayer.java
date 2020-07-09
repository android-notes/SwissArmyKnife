package com.wanjian.sak.layer.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.ISize;
import com.wanjian.sak.utils.ScreenUtils;

import java.text.DecimalFormat;


/**
 * Created by wanjian on 2016/10/23.
 */

public class MarginLayer extends ViewLayer implements ISize {

  private Paint mTextPaint;
  private Paint mBgPaint;
  private Paint mMarginPaint;
  private int mTxtColor = 0xFF000000;
  private int mBgColor = 0x88FFFFFF;
  private int mMarginColor = 0x33FF0008;
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

    mMarginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mMarginPaint.setColor(mMarginColor);
  }

  @Override
  protected void onDraw(Canvas canvas, View view) {
    if (!(view instanceof ViewGroup)) {
      return;
    }
    int childCount = ((ViewGroup) view).getChildCount();
    for (int i = 0; i < childCount; i++) {
      canvas.save();
      drawMargin(canvas, ((ViewGroup) view).getChildAt(i));
      canvas.restore();
    }

  }

  private void drawMargin(Canvas canvas, View view) {
    ViewGroup.LayoutParams params = view.getLayoutParams();
    if (!(params instanceof ViewGroup.MarginLayoutParams)) {
      return;
    }//画 外边距
    int w = view.getWidth();
    int h = view.getHeight();
    ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);

    canvas.translate(view.getX(), view.getY());

    int l = -marginLayoutParams.leftMargin;
    int t = -marginLayoutParams.topMargin;
    int r = w + marginLayoutParams.rightMargin;
    int b = h + marginLayoutParams.bottomMargin;

    canvas.drawRect(l, 0, 0, h, mMarginPaint);//left
    canvas.drawRect(w, 0, r, h, mMarginPaint);//right
    canvas.drawRect(0, t, w, 0, mMarginPaint);
    canvas.drawRect(0, h, w, b, mMarginPaint);
    ISizeConverter converter = getSizeConverter();
    Context context = getContext();

    if (marginLayoutParams.leftMargin != 0) {
      String txt = "L" + mFormat.format(converter.convert(context, marginLayoutParams.leftMargin).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(l, h / 2f - mRect.height() / 2f, l + mRect.width(), h / 2f + mRect.height() / 2f, mBgPaint);
      canvas.drawText(txt, l, h / 2f + mRect.height() / 2f, mTextPaint);
    }
    if (marginLayoutParams.topMargin != 0) {
      String txt = "T" + mFormat.format(converter.convert(context, marginLayoutParams.topMargin).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(w / 2f - mRect.width() / 2f, t, w / 2f + mRect.width() / 2f, t + mRect.height(), mBgPaint);
      canvas.drawText(txt, w / 2f - mRect.width() / 2f, t + mRect.height(), mTextPaint);
    }

    if (marginLayoutParams.rightMargin != 0) {
      String txt = "R" + mFormat.format(converter.convert(context, marginLayoutParams.rightMargin).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(r - mRect.width(), h / 2f - mRect.height() / 2f, r, h / 2f + mRect.height() / 2f, mBgPaint);
      canvas.drawText(txt, r - mRect.width(), h / 2f + mRect.height() / 2f, mTextPaint);
    }

    if (marginLayoutParams.bottomMargin != 0) {
      String txt = "B" + mFormat.format(converter.convert(context, marginLayoutParams.bottomMargin).getLength());
      mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
      canvas.drawRect(w / 2f - mRect.width() / 2f, b - mRect.height(), w / 2f + mRect.width() / 2f, b, mBgPaint);
      canvas.drawText(txt, w / 2f - mRect.width() / 2f, b, mTextPaint);
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
