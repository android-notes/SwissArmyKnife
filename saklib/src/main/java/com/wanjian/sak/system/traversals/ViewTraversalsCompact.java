package com.wanjian.sak.system.traversals;

import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.unsafe.UnsafeProxy;

import java.lang.reflect.Field;

public class ViewTraversalsCompact {
  static Field sTraversalRunnableF;

  static {
    try {
      sTraversalRunnableF = ViewRootImpl.class.getDeclaredField("mTraversalRunnable");
      sTraversalRunnableF.setAccessible(true);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void register(ViewRootImpl viewRootImpl, View view, ViewTraversalsListener listener) {

    Runnable originTraversal = null;
    try {
      originTraversal = (Runnable) sTraversalRunnableF.get(viewRootImpl);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    if (originTraversal instanceof MyTraversalRunnable) {
      ((MyTraversalRunnable) originTraversal).addTraversalsListener(listener);
      return;
    }

    MyTraversalRunnable myTraversal = new MyTraversalRunnable(originTraversal, view);

    myTraversal.addTraversalsListener(listener);

    long offset = UnsafeProxy.objectFieldOffset(sTraversalRunnableF);

    UnsafeProxy.putObject(viewRootImpl, offset, myTraversal);
  }
}
