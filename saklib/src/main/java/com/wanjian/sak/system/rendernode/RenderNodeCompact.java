package com.wanjian.sak.system.rendernode;

import android.graphics.Canvas;
import android.os.Build;

public abstract class RenderNodeCompact {
  RenderNodeCompact() {

  }

  public static RenderNodeCompact create(String name) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      return new RenderNodeV29Impl(name);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return new RenderNodeV21Impl(name);
    } else {
      throw new RuntimeException("unsupport android version");
    }
  }

  public abstract void drawRenderNode(Canvas canvas);

  public abstract Canvas beginRecording(int width, int height);

  public abstract void endRecording(Canvas canvas);

  public abstract boolean isValid();
}
