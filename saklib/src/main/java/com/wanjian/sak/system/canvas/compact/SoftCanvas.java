package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;

class SoftCanvas extends CanvasCompact {

  private int count;
  private Rect rect = new Rect();
  private Canvas canvas;
  private Surface surface;

  SoftCanvas(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);

  }

  @Override
  public Canvas requireCanvas() {
    if (count != 0) {
      throw new IllegalStateException("count == " + count);
    }
    count++;
    rect.left = 0;
    rect.top = 0;
    View rootView = viewRootImpl.getView();
    rect.right = rootView.getWidth();
    rect.bottom = rootView.getHeight();
    canvas = getSurface().lockCanvas(rect);
    return canvas;
  }

  @Override
  public void releaseCanvas() {
    if (count != 1) {
      throw new IllegalStateException("count == " + count);
    }
    count--;
    getSurface().unlockCanvasAndPost(canvas);
  }


  private Surface getSurface() {
    if (surface != null) {
      return surface;
    }
    try {
      Field mSurfaceF = ViewRootImpl.class.getDeclaredField("mSurface");
      mSurfaceF.setAccessible(true);
      surface = (Surface) mSurfaceF.get(viewRootImpl);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return surface;
  }


}
