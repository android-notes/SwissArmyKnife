package com.wanjian.sak.system.canvas.compact;

import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Method;

public class HardwareCanvasV24Impl extends HardwareCanvasV23Impl {
  HardwareCanvasV24Impl(ViewRootImpl viewRootImpl) {
    super(viewRootImpl);
  }

  protected boolean isThreadRendererEnable(ThreadedRenderer threadRenderer) {
    try {
      Method isEnabledM = ThreadedRenderer.class.getDeclaredMethod("isEnabled");
      isEnabledM.setAccessible(true);
      return (boolean) isEnabledM.invoke(threadRenderer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
