package android.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Pools;

public class DisplayListCanvas extends Canvas {

  private static final int POOL_LIMIT = 25;
  //android 6.0,7.0,7.1,8.0,8.1,9.0
  private static final Pools.SynchronizedPool<DisplayListCanvas> sPool =
      new Pools.SynchronizedPool<DisplayListCanvas>(POOL_LIMIT);

  public void onPreDraw(Rect dirty) {
  }

  public void insertReorderBarrier() {
  }

  public void insertInorderBarrier() {
  }

  public void drawRenderNode(RenderNode renderNode) {
  }
}
