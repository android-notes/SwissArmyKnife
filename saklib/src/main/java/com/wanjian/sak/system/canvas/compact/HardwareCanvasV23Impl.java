package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.view.Choreographer;
import android.view.DisplayListCanvas;
import android.view.RenderNode;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HardwareCanvasV23Impl extends HardwareCanvasV21Impl {

  private DisplayListCanvas canvas;

  HardwareCanvasV23Impl(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);
  }

  protected Canvas innerRequire() {
    if (threadRenderer != null && isThreadRendererEnable(threadRenderer)) {
      markDrawStart(Choreographer.getInstance());
      mRootNode = getRooNode(threadRenderer);
      canvas = mRootNode.start(getSurfaceW(threadRenderer), getSurfaceH(threadRenderer));

      saveCount = canvas.save();
      canvas.translate(getInsertLeft(), getInsertTop());
      canvas.translate(-getHardwareXOffset(), -getHardwareYOffset());
      canvas.insertReorderBarrier();
      canvas.drawRenderNode(getUpdateDisplayListIfDirty(viewRootImpl.getView()));
      canvas.insertInorderBarrier();
      return canvas;
    } else {
      mRootNode = null;
      return getSoftCanvas().requireCanvas();
    }
  }
  protected void innerRelease() {
    if (mRootNode != null) {
//      canvas.insertInorderBarrier();
      canvas.restoreToCount(saveCount);
      mRootNode.end(canvas);
      nSyncAndDrawFrame();
    } else {
      getSoftCanvas().releaseCanvas();
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

  private RenderNode getUpdateDisplayListIfDirty(View view) {
    try {
      Method method = View.class.getDeclaredMethod("updateDisplayListIfDirty");
      method.setAccessible(true);
      return (RenderNode) method.invoke(view);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
