package com.wanjian.sak.layer.impl;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.Utils;

import java.util.List;

public class FragmentNameLayer extends ActivityNameLayerView {

  private int mStartLayer;
  private int mEndLayer = 100;// TODO: 2020/7/7
  private FragmentActivity mActivity;
  private Paint mBagPaint = new Paint();

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
    List<Fragment> fragments = fragmentManager.getFragments();
    if (fragments == null || fragments.isEmpty()) {
      return;
    }
    if (curLayer > mEndLayer) {
      return;
    }

    if (curLayer >= mStartLayer) {
      for (Fragment fragment : fragments) {
        if (fragment == null) {
          continue;
        }
        if (fragment.isVisible()) {
          View view = fragment.getView();
          if (view != null) {
            int localSize[] = getLocationAndSize(view);
            canvas.drawRect(localSize[0], localSize[1], localSize[0] + localSize[2], localSize[1] + localSize[3], mBagPaint);
            drawInfo(canvas, localSize[0], localSize[1], localSize[2], localSize[3], fragment.getClass().getName());
          }
        }
      }
    }
    for (Fragment fragment : fragments) {
      if (fragment.isVisible()) {
        View view = fragment.getView();
        if (view != null) {
          traversal(canvas, fragment.getChildFragmentManager(), curLayer + 1);
        }
      }
    }


  }

  protected int[] getLocationAndSize(View view) {
    int[] locations = new int[2];
    view.getLocationInWindow(locations);
    View decorView = view.getRootView();// dialog 内边距
    return new int[]{locations[0] - decorView.getPaddingLeft(), locations[1] - decorView.getPaddingTop(), view.getWidth(), view.getHeight()};
  }
}
