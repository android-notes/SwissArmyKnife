package android.graphics;

import android.util.Pools;

public class RecordingCanvas extends Canvas {
  private static final int POOL_LIMIT = 25;
  private static final Pools.SynchronizedPool<RecordingCanvas> sPool =
      new Pools.SynchronizedPool<>(POOL_LIMIT);


  public void enableZ() {
  }

  public void disableZ() {
  }

  public void drawRenderNode(RenderNode renderNode) {
  }
}
