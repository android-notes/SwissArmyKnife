package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.layer.IRange;
import com.wanjian.sak.layer.Layer;

public abstract class ViewLayer extends Layer implements IRange {
  private int mStartLayer;
  private int mEndLayer=100;
  private boolean isClip = true;

  @Override
  public void onStartRangeChange(int start) {
    mStartLayer = start;
    invalidate();
  }

  @Override
  public void onEndRangeChange(int end) {
    mEndLayer = end;
    invalidate();
  }

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
  }

  @Override
  protected final void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    traversals(canvas, getRootView(), 0);
  }

  @Override
  protected void onAfterTraversal(View rootView) {
    super.onAfterTraversal(rootView);
    invalidate();
  }

//
//  public void setStartLayer(int startLayer) {
//    this.mStartLayer = startLayer;
//  }
//
//  public void setEndLayer(int endLayer) {
//    this.mEndLayer = endLayer;
//  }

//  public void setClip(boolean clip) {
//    isClip = clip;
//  }

  private void traversals(Canvas canvas, View view, int curLayer) {
    if (curLayer > mEndLayer) {
      return;
    }
    if (curLayer >= mStartLayer) {
      int count = canvas.save();
      onDraw(canvas, view);
      canvas.restoreToCount(count);
    }

    if (view instanceof ViewGroup) {
      ViewGroup vg = ((ViewGroup) view);
      int sx = view.getScrollX();
      int sy = view.getScrollY();
      canvas.save(); //save 1
      canvas.translate(-sx, -sy);

      for (int i = 0, len = vg.getChildCount(); i < len; i++) {
        View child = vg.getChildAt(i);
        canvas.save();//save 2
        canvas.translate(child.getX(), child.getY());
        if (isClip) {
          canvas.clipRect(0, 0, child.getWidth(), child.getHeight(), Region.Op.INTERSECT);
        }

//        canvas.save(); //save 3
        traversals(canvas, child, curLayer + 1);
//        canvas.restore();// restore 3

        canvas.restore();// restore 2
      }
      canvas.restore(); // restore 1
    }
  }

  protected abstract void onDraw(Canvas canvas, View view);

}
