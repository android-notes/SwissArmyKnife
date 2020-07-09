package com.wanjian.sak.system.window.compact;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

/*
 * get the root view of all windows
 * <p>
 * when you add view by windowManager.addView(), the root view may not be a DecorView
 * <p>
 * <p>
 * There are some differences when you want to get the root view
 * <p>
 * 4.0.3_r1    WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.0.4       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.1.1       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.1.2       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.2_r1      WindowManagerGlobal    private View[] mViews
 * <p>
 * 4.2.2 r1    WindowManagerGlobal    private View[] mViews
 * <p>
 * 4.3_r2.1    WindowManagerGlobal    private View[] mViews;
 * <p>
 * 4.4_r1      WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 4.4.2_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 5.0.0_r2    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 6.0.0_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 7.0.0_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 8.0.0_r4    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 */
public abstract class WindowRootViewCompat {

  public static WindowRootViewCompat get(Context context) {
    WindowRootViewCompat windowRootViewCompat;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      windowRootViewCompat = new WindowRootViewCompactV19Impl();
    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
        || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
      throw new RuntimeException("unsupport");
//      windowRootViewCompat = new WindowRootViewCompactV18Impl();
    } else {
      throw new RuntimeException("unsupport");
//      windowRootViewCompat = new WindowRootViewCompactV16Impl(context.getApplicationContext());
    }
    return windowRootViewCompat;
  }

  public final void addWindowChangeListener(IWindowChangeListener changeListener) {
    if (changeListener == null) {
      return;
    }
    onAddWindowChangeListener(changeListener);
  }

  public final void removeWindowChangeListener(IWindowChangeListener changeListener) {
    if (changeListener == null) {
      return;
    }
    onRemoveWindowChangeListener(changeListener);
  }

  abstract void onAddWindowChangeListener(@NonNull IWindowChangeListener changeListener);

  abstract void onRemoveWindowChangeListener(@NonNull IWindowChangeListener changeListener);

}
