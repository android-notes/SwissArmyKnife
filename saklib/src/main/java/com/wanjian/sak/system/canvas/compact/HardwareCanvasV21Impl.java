package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.util.Log;
import android.view.HardwareCanvas;
import android.view.HardwareRenderer;
import android.view.RenderNode;
import android.view.Surface;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HardwareCanvasV21Impl extends CanvasCompact {

  private int count;
  private SoftCanvas softCanvas;
  ThreadedRenderer threadRenderer;
  private RenderNode mRootNode;
  private HardwareCanvas canvas;
  private int saveCount;
  private long frameTimeNanos;

  HardwareCanvasV21Impl(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);

    threadRenderer = getHardwareRenderer(viewRootImpl);
  }


  @Override
  public final Canvas requireCanvas() {
    Surface surface = getSurface();
    if (!surface.isValid()) {
      Log.w("SAK", "surface invalidate");
      return null;
    }

    if (count != 0) {
      throw new IllegalStateException("count == " + count);
    }
    count++;

    return innerRequire();
  }

  protected Canvas innerRequire() {
    if (threadRenderer != null && isThreadRendererEnable(threadRenderer)) {
      mRootNode = getRooNode(threadRenderer);
//      public long getFrameTimeNanos() {
      try {
//        Method getFrameTimeNanosM = Choreographer.class.getDeclaredMethod("getFrameTimeNanos");
//        getFrameTimeNanosM.setAccessible(true);
//        frameTimeNanos = (long) getFrameTimeNanosM.invoke(Choreographer.getInstance());
        frameTimeNanos = System.nanoTime();// TODO: 2020/7/5  getFrameTimeNanos crash
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
//      canvas =   mRootNode.start(getSurfaceW(threadRenderer), getSurfaceH(threadRenderer));
      try {
        Method startM = RenderNode.class.getDeclaredMethod("start", int.class, int.class);
        startM.setAccessible(true);
        canvas = (HardwareCanvas) startM.invoke(mRootNode, getSurfaceW(threadRenderer), getSurfaceH(threadRenderer));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      saveCount = canvas.save();
      canvas.insertReorderBarrier();
      canvas.drawRenderNode(getDisplayList());
      return canvas;
    } else {
      mRootNode = null;
      return getSoftCanvas().requireCanvas();
    }
  }

  @Override
  public final void releaseCanvas() {
    if (count != 1) {
      throw new IllegalStateException("count == " + count);
    }
    count--;
    innerRelease();
  }

  protected void innerRelease() {
    if (mRootNode != null) {
      canvas.insertInorderBarrier();
      canvas.restoreToCount(saveCount);
      mRootNode.end(canvas);
      nSyncAndDrawFrame();
    } else {
      getSoftCanvas().releaseCanvas();
    }
  }

  protected void nSyncAndDrawFrame() {
//    int syncResult = nSyncAndDrawFrame(mNativeProxy, frameTimeNanos,
//                           recordDuration, view.getResources().getDisplayMetrics().density);
    nSyncAndDrawFrame(viewRootImpl, getNativeProxy(viewRootImpl), frameTimeNanos, 0, viewRootImpl.getView().getResources().getDisplayMetrics().density);
  }

  private void nSyncAndDrawFrame(ViewRootImpl viewRoot, long nativeProxy, long frameTimeNanos, int i, float density) {
//    private static native int nSyncAndDrawFrame(long nativeProxy,
//                  long frameTimeNanos, long recordDuration, float density);
    try {
      Method nSyncAndDrawFrameM = ThreadedRenderer.class.getDeclaredMethod("nSyncAndDrawFrame", long.class, long.class, long.class, float.class);
      nSyncAndDrawFrameM.setAccessible(true);
      ThreadedRenderer hardwareRenderer = getHardwareRenderer(viewRootImpl);
      nSyncAndDrawFrameM.invoke(hardwareRenderer, nativeProxy, frameTimeNanos, i, density);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected long getNativeProxy(ViewRootImpl viewRootImpl) {
//    private long mNativeProxy;
//    mAttachInfo.mHardwareRenderer
    try {
      ThreadedRenderer hardwareRenderer = getHardwareRenderer(viewRootImpl);

      Field mNativeProxyF = hardwareRenderer.getClass().getDeclaredField("mNativeProxy");
      mNativeProxyF.setAccessible(true);
      return (long) mNativeProxyF.get(hardwareRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }


  private RenderNode getDisplayList() {
    try {
      Method method = View.class.getDeclaredMethod("getDisplayList");
      method.setAccessible(true);
      return (RenderNode) method.invoke(viewRootImpl.getView());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  protected boolean isThreadRendererEnable(ThreadedRenderer threadRenderer) {
    try {
      Method isEnabledM = HardwareRenderer.class.getDeclaredMethod("isEnabled");
      isEnabledM.setAccessible(true);
      return (boolean) isEnabledM.invoke(threadRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected ThreadedRenderer getHardwareRenderer(ViewParent viewRootImpl) {
    try {
      Field mAttachInfoF = ViewRootImpl.class.getDeclaredField("mAttachInfo");
      mAttachInfoF.setAccessible(true);
      Object mAttachInfo = mAttachInfoF.get(viewRootImpl);
      Field threadedRendererF;

      threadedRendererF = mAttachInfo.getClass().getDeclaredField("mHardwareRenderer");

      threadedRendererF.setAccessible(true);
      return (ThreadedRenderer) threadedRendererF.get(mAttachInfo);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  CanvasCompact getSoftCanvas() {
    if (softCanvas == null) {
      softCanvas = new SoftCanvas(viewRootImpl);
    }
    return softCanvas;
  }

  Surface getSurface() {
    try {
      Field mSurfaceF = ViewRootImpl.class.getDeclaredField("mSurface");
      mSurfaceF.setAccessible(true);
      return (Surface) mSurfaceF.get(viewRootImpl);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  int getSurfaceW(ThreadedRenderer hardwareRenderer) {
    try {
      Field mSurfaceWidthF = hardwareRenderer.getClass().getDeclaredField("mSurfaceWidth");
      mSurfaceWidthF.setAccessible(true);

      return (int) mSurfaceWidthF.get(hardwareRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  int getSurfaceH(ThreadedRenderer hardwareRenderer) {
    try {
      Field mSurfaceWidthF = hardwareRenderer.getClass().getDeclaredField("mSurfaceHeight");
      mSurfaceWidthF.setAccessible(true);

      return (int) mSurfaceWidthF.get(hardwareRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private RenderNode getRooNode(ThreadedRenderer hardwareRenderer) {
    try {
      Field mRootNodeF = hardwareRenderer.getClass().getDeclaredField("mRootNode");
      mRootNodeF.setAccessible(true);
      return (RenderNode) mRootNodeF.get(hardwareRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
