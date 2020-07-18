package com.wanjian.sak.layer.impl;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.system.canvaspool.CanvasPoolCompact;
import com.wanjian.sak.system.canvaspool.CanvasRecycleListener;

public class FlashWindowLayer extends Layer {
  private CanvasRecycleListener listener = new CanvasRecycleListener() {
    @Override
    public void onRecycle(Canvas canvas) {
      if (traversaling == false || flash) {
        return;
      }
      flash();
    }
  };
  private Handler handler = new Handler(Looper.getMainLooper());
  private boolean flash;

  private void flash() {
    flash = true;
    handler.post(new Runnable() {
      @Override
      public void run() {
        invalidate();
      }
    });
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        flash = false;
      }
    }, 1000);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (flash == false) {
      return;
    }
    int color = 0x88000000 | (int) (Math.random() * 0xFFFFFF);
    canvas.drawColor(color);
    handler.post(new Runnable() {
      @Override
      public void run() {
        invalidate();
      }
    });
  }

  private boolean traversaling;

  @Override
  protected void onBeforeTraversal(View rootView) {
    super.onBeforeTraversal(rootView);
    traversaling = true;
  }

  @Override
  protected void onAfterTraversal(View rootView) {
    super.onAfterTraversal(rootView);
    traversaling = false;
  }

  @Override
  protected void onEnableChange(boolean enable) {
    super.onEnableChange(enable);
    if (enable) {
      CanvasPoolCompact.get().registerListener(listener);
    } else {
      CanvasPoolCompact.get().removeListener(listener);
    }
  }
}
