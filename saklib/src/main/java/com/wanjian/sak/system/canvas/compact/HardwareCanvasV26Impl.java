package com.wanjian.sak.system.canvas.compact;

import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;

class HardwareCanvasV26Impl extends HardwareCanvasV24Impl {

  HardwareCanvasV26Impl(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);
  }


  protected ThreadedRenderer getHardwareRenderer(ViewRootImpl viewRootImpl) {
    try {
      Field mAttachInfoF = ViewRootImpl.class.getDeclaredField("mAttachInfo");
      mAttachInfoF.setAccessible(true);
      Object mAttachInfo = mAttachInfoF.get(viewRootImpl);
      Field threadedRendererF;
      threadedRendererF = mAttachInfo.getClass().getDeclaredField("mThreadedRenderer");
      threadedRendererF.setAccessible(true);
      return (ThreadedRenderer) threadedRendererF.get(mAttachInfo);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
