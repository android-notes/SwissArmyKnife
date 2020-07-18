package android.view;

import android.util.Pools;

class GLES20RecordingCanvas {
  // The recording canvas pool should be large enough to handle a deeply nested
  // view hierarchy because display lists are generated recursively.
  private static final int POOL_LIMIT = 25;
  //android 5.0,5.1
  private static final Pools.SynchronizedPool<GLES20RecordingCanvas> sPool =
      new Pools.SynchronizedPool<GLES20RecordingCanvas>(POOL_LIMIT);

}