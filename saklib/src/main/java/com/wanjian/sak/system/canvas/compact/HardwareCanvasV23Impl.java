package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.util.Log;
import android.view.Choreographer;
import android.view.DisplayListCanvas;
import android.view.HardwareRenderer;
import android.view.RenderNode;
import android.view.Surface;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HardwareCanvasV23Impl extends CanvasCompact {

  private int count;
  private SoftCanvas softCanvas;
  ThreadedRenderer threadRenderer;
  private RenderNode mRootNode;
  private DisplayListCanvas canvas;
  private int saveCount;

  HardwareCanvasV23Impl(ViewRootImpl viewRootImpl) {
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
      markDrawStart(Choreographer.getInstance());
      mRootNode = getRooNode(threadRenderer);
      canvas = mRootNode.start(getSurfaceW(threadRenderer), getSurfaceH(threadRenderer));

      saveCount = canvas.save();
      translate(canvas, threadRenderer);
      canvas.insertReorderBarrier();
      canvas.drawRenderNode(getUpdateDisplayListIfDirty(viewRootImpl.getView()));
      return canvas;
    } else {
      mRootNode = null;
      return getSoftCanvas().requireCanvas();
    }
  }

  protected void markDrawStart(Choreographer choreographer) {

//    final Choreographer choreographer = attachInfo.mViewRootImpl.mChoreographer;
//    choreographer.mFrameInfo.markDrawStart();
    try {
      Field mFrameInfoF = Choreographer.class.getDeclaredField("mFrameInfo");
      mFrameInfoF.setAccessible(true);
      Object mFrameInfo = mFrameInfoF.get(choreographer);

      Method markDrawStartM = mFrameInfo.getClass().getDeclaredMethod("markDrawStart");
      markDrawStartM.setAccessible(true);
      markDrawStartM.invoke(mFrameInfo);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected void translate(Canvas canvas, ThreadedRenderer threadRenderer) {
    try {
      Field mInsetLeftF = ThreadedRenderer.class.getDeclaredField("mInsetLeft");
      mInsetLeftF.setAccessible(true);
      int left = (int) mInsetLeftF.get(threadRenderer);

      Field mInsetTopF = ThreadedRenderer.class.getDeclaredField("mInsetTop");
      mInsetTopF.setAccessible(true);
      int top = (int) mInsetTopF.get(threadRenderer);

      canvas.translate(left, top);
    } catch (Exception e) {
      throw new RuntimeException(e);
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
    final long[] frameInfo = getFrameInfo(Choreographer.getInstance());
    nSyncAndDrawFrame(getNativeProxy(viewRootImpl), frameInfo, frameInfo.length);
  }

  private static void nSyncAndDrawFrame(long nativeProxy, long[] frameInfo, int length) {
//    private static native int nSyncAndDrawFrame(long nativeProxy, long[] frameInfo, int size);
    try {
      Method nSyncAndDrawFrameM = ThreadedRenderer.class.getDeclaredMethod("nSyncAndDrawFrame", long.class, long[].class, int.class);
      nSyncAndDrawFrameM.setAccessible(true);
      nSyncAndDrawFrameM.invoke(null, nativeProxy, frameInfo, length);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static long[] getFrameInfo(Choreographer choreographer) {
//    choreographer.mFrameInfo.mFrameInfo;
    try {

      Field mFrameInfoF = Choreographer.class.getDeclaredField("mFrameInfo");
      mFrameInfoF.setAccessible(true);
      Object mFrameInfo = mFrameInfoF.get(choreographer);

      Field mFrameInfoF2 = mFrameInfo.getClass().getDeclaredField("mFrameInfo");
      mFrameInfoF2.setAccessible(true);
      return (long[]) mFrameInfoF2.get(mFrameInfo);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }


  private long getNativeProxy(ViewRootImpl viewRootImpl) {
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

  protected boolean isThreadRendererEnable(ThreadedRenderer threadRenderer) {
    try {
      Method isEnabledM = HardwareRenderer.class.getDeclaredMethod("isEnabled");
      isEnabledM.setAccessible(true);
      return (boolean) isEnabledM.invoke(threadRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected ThreadedRenderer getHardwareRenderer(ViewRootImpl viewRootImpl) {
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

  private RenderNode getUpdateDisplayListIfDirty(View view) {
    try {
      Method method = View.class.getDeclaredMethod("updateDisplayListIfDirty");
      method.setAccessible(true);
      return (RenderNode) method.invoke(view);
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
