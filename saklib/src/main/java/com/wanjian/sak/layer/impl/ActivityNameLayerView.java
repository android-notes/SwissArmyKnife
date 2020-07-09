package com.wanjian.sak.layer.impl;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.ScreenUtils;
import com.wanjian.sak.utils.Utils;

public class ActivityNameLayerView extends Layer {

  private Paint mPaint;
  private String mActName;
  private int mRadius;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setTextSize(ScreenUtils.dp2px(getContext(), 10));
    mPaint.setColor(Color.RED);
    mPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(), 1));
    mRadius = ScreenUtils.dp2px(getContext(), 8);
    getActivityName(rootView);
  }

  protected void getActivityName(View view) {
    Activity activity = Utils.findAct(view);
    if (activity == null) {
      return;
    }
    mActName = activity.getClass().getName();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawActivityInfo(canvas);
  }

  protected void drawActivityInfo(Canvas canvas) {
    if (mActName == null) {
      return;
    }
    drawInfo(canvas, 0, 0, canvas.getWidth(), canvas.getHeight(), mActName);
  }


  protected void drawInfo(Canvas canvas, int x, int y, int w, int h, String txt) {
    canvas.save();
    drawCorner(canvas, x, y, w, h);

    drawX(canvas, x, y, w, h);

    drawName(canvas, x, y, w, h, txt);
    canvas.restore();
  }

  private void drawName(Canvas canvas, int x, int y, int w, int h, String txt) {
    TextPaint textPaint = new TextPaint(mPaint);
    textPaint.setTextSize(ScreenUtils.dp2px(getContext(), 13));
    canvas.translate(x, y);
    SpannableString decorStr = new SpannableString(txt);
    decorStr.setSpan(new BackgroundColorSpan(0x99FFFFFF), 0, txt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    StaticLayout staticLayout = new StaticLayout(decorStr, textPaint, (int) (w * 0.8), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

    int txtW = staticLayout.getWidth();
    int txtH = staticLayout.getHeight();
    if (txtH > 0 && txtH > h * 0.8) {
      float scale = h * 0.8f / txtH;
      int realW = (int) (txtW * scale);
      int realH = (int) (txtH * scale);

      canvas.translate((w - realW) / 2f, (h - realH) / 2f);
      canvas.scale(scale, scale);
    } else {
      canvas.translate((w - txtW) / 2f, (h - txtH) / 2f);
    }

    staticLayout.draw(canvas);
  }

  private void drawX(Canvas canvas, int x, int y, int w, int h) {
    canvas.drawLine(x, y, x + w, y + h, mPaint);
    canvas.drawLine(x + w, y, x, y + h, mPaint);
  }

  private void drawCorner(Canvas canvas, int x, int y, int w, int h) {
    canvas.drawLine(x, y, x + mRadius, y, mPaint);
    canvas.drawLine(x, y, x, y + mRadius, mPaint);

    canvas.drawLine(x + w, y, x + w - mRadius, y, mPaint);
    canvas.drawLine(x + w, y, x + w, y + mRadius, mPaint);


    canvas.drawLine(x, y + h, x + mRadius, y + h, mPaint);
    canvas.drawLine(x, y + h, x, y + h - mRadius, mPaint);


    canvas.drawLine(x + w, y + h, x + w - mRadius, y + h, mPaint);
    canvas.drawLine(x + w, y + h, x + w, y + h - mRadius, mPaint);
  }
}
