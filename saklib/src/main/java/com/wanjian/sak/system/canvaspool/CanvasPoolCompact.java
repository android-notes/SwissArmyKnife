package com.wanjian.sak.system.canvaspool;

import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.os.Build;
import android.util.Pools;
import android.view.DisplayListCanvas;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CanvasPoolCompact {
  private static CanvasPoolCompact sInstance = new CanvasPoolCompact();

  public static CanvasPoolCompact get() {
    return sInstance;
  }

  private List<WeakReference<CanvasRecycleListener>> listeners = new ArrayList<>();

  private CanvasPoolCompact() {
    Class clz;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      clz = RecordingCanvas.class;
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      clz = DisplayListCanvas.class;
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      try {
        clz = Class.forName("android.view.GLES20RecordingCanvas");
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new RuntimeException();
    }
    final Pools.SynchronizedPool origin;
    Field field;
    try {
      field = clz.getDeclaredField("sPool");
      field.setAccessible(true);
      origin = (Pools.SynchronizedPool) field.get(null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    Pools.SynchronizedPool pool = new Pools.SynchronizedPool(1) {
      @Override
      public Object acquire() {
        return origin.acquire();
      }

      @Override
      public boolean release(Object element) {
        boolean b = origin.release(element);
        Iterator<WeakReference<CanvasRecycleListener>> iterator = listeners.iterator();
        while (iterator.hasNext()) {
          WeakReference<CanvasRecycleListener> reference = iterator.next();
          CanvasRecycleListener recycleListener = reference.get();
          if (recycleListener == null) {
            iterator.remove();
          } else {
            recycleListener.onRecycle((Canvas) element);
          }
        }
        return b;
      }
    };

    try {
      field.set(null, pool);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public void registerListener(CanvasRecycleListener listener) {
    listeners.add(new WeakReference<>(listener));
  }

  public void removeListener(CanvasRecycleListener listener) {
    Iterator<WeakReference<CanvasRecycleListener>> iterator = listeners.iterator();
    while (iterator.hasNext()) {
      WeakReference<CanvasRecycleListener> reference = iterator.next();
      CanvasRecycleListener recycleListener = reference.get();
      if (recycleListener == null || recycleListener == listener) {
        iterator.remove();
      }
    }
  }


}
