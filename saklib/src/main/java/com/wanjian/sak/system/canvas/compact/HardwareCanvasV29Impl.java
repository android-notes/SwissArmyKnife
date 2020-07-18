package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.view.Choreographer;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HardwareCanvasV29Impl extends HardwareCanvasV26Impl {
  private RenderNode mRootNode;
  private RecordingCanvas canvas;
  private int saveCount;

  HardwareCanvasV29Impl(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);
  }

  @Override
  protected Canvas innerRequire() {
    if (threadRenderer != null && isThreadRendererEnable(threadRenderer)) {
      markDrawStart(Choreographer.getInstance());
      mRootNode = getRooNodeV29(threadRenderer);
      canvas = mRootNode.beginRecording(getSurfaceW(threadRenderer), getSurfaceH(threadRenderer));
      canvas.translate(getInsertLeft(), getInsertTop());
      canvas.translate(-getHardwareXOffset(), -getHardwareYOffset());
      saveCount = canvas.save();
      canvas.enableZ();
      canvas.drawRenderNode(getUpdateDisplayListIfDirty());
      canvas.disableZ();
      return canvas;
    } else {
      mRootNode = null;
      return getSoftCanvas().requireCanvas();
    }
  }

  @Override
  protected void innerRelease() {
    if (mRootNode != null) {
//      canvas.disableZ();
      canvas.restoreToCount(saveCount);
      mRootNode.endRecording();
      nSyncAndDrawFrame();
    } else {
      getSoftCanvas().releaseCanvas();
    }
  }

  private RenderNode getUpdateDisplayListIfDirty() {
    try {
      Method method = View.class.getDeclaredMethod("updateDisplayListIfDirty");
      method.setAccessible(true);
      return (RenderNode) method.invoke(viewRootImpl.getView());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected RenderNode getRooNodeV29(ThreadedRenderer hardwareRenderer) {
    try {
      Field mRootNodeF = HardwareRenderer.class.getDeclaredField("mRootNode");
      mRootNodeF.setAccessible(true);
      return (RenderNode) mRootNodeF.get(hardwareRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void markDrawStart(Choreographer choreographer) {
    try {
      FrameInfo frameInfo = getFrameInfo();
      frameInfo.markDrawStart();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private FrameInfo getFrameInfo() {
    try {
      Field mFrameInfoF = Choreographer.class.getDeclaredField("mFrameInfo");
      mFrameInfoF.setAccessible(true);
      FrameInfo frameInfo = (FrameInfo) mFrameInfoF.get(Choreographer.getInstance());
      return frameInfo;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void nSyncAndDrawFrame() {
//    int syncResult = syncAndDrawFrame(choreographer.mFrameInfo);
    try {
      ThreadedRenderer renderer = getHardwareRenderer(viewRootImpl);
      Method method = android.graphics.HardwareRenderer.class.getDeclaredMethod("syncAndDrawFrame", FrameInfo.class);
      method.setAccessible(true);
      method.invoke(renderer, getFrameInfo());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
