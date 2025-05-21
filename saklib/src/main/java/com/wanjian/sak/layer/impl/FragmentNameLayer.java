package com.wanjian.sak.layer.impl;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.wanjian.sak.layer.IClip;
import com.wanjian.sak.layer.IRange;
import com.wanjian.sak.utils.Utils;

import java.util.List;

public class FragmentNameLayer extends ActivityNameLayerView implements IRange, IClip {

  private int mStartLayer;
  private int mEndLayer = 100;// TODO: 2020/7/7
  private FragmentActivity mActivity;
  private Paint mBagPaint = new Paint();
  private boolean isClip = true;

  @Override
  protected void onAttach(View rootView) {
    super.onAttach(rootView);
    mBagPaint.setColor(Color.argb(63, 255, 0, 255));
    Activity activity = Utils.findAct(rootView);
    if (activity instanceof FragmentActivity) {
      mActivity = (FragmentActivity) activity;
      invalidate();
    }
  }


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
  protected void onAfterTraversal(View rootView) {
    super.onAfterTraversal(rootView);
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas);
    if (mActivity == null || mActivity.isFinishing()) {
      return;
    }
//    mStartLayer = getStartRange();
//    mEndLayer = getEndRange();
    traversal(canvas, mActivity.getSupportFragmentManager(), 0);
  }


  private void traversal(Canvas canvas, FragmentManager fragmentManager, int curLayer) {
    if (curLayer > mEndLayer || fragmentManager == null) {
      return;
    }
    List<Fragment> fragments = fragmentManager.getFragments();
    if (fragments == null || fragments.isEmpty()) {
      return;
    }

    for (Fragment fragment : fragments) {
      if (fragment.isVisible() && curLayer >= mStartLayer) {
        View view = fragment.getView();
//        int localSize[] = getLocationAndSize(view);
//        canvas.drawRect(localSize[0], localSize[1], localSize[0] + localSize[2], localSize[1] + localSize[3], mBagPaint);
//        drawInfo(canvas, localSize[0], localSize[1], localSize[2], localSize[3], fragment.getClass().getName());
        canvas.save();
        traversals(view, canvas, getRootView(), fragment.getClass().getName());
        canvas.restore();
      }
      traversal(canvas, fragment.getChildFragmentManager(), curLayer + 1);
    }
  }

  private void traversals(View target, Canvas canvas, View curView, String name) {
    if (target == curView) {
      canvas.drawRect(0, 0, target.getWidth(), target.getHeight(), mBagPaint);
      drawInfo(canvas, 0, 0, target.getWidth(), target.getHeight(), name);
      return;
    }

    if (curView instanceof ViewGroup) {
      ViewGroup vg = ((ViewGroup) curView);
      int sx = curView.getScrollX();
      int sy = curView.getScrollY();
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
        traversals(target, canvas, child, name);
//        canvas.restore();// restore 3

        canvas.restore();// restore 2
      }
      canvas.restore(); // restore 1
    }
  }

  @Override
  public void onClipChange(boolean clip) {
    isClip = clip;
    invalidate();
  }
}
