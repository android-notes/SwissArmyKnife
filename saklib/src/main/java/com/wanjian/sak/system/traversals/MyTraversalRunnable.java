package com.wanjian.sak.system.traversals;

import android.util.Log;
import android.view.View;
import android.view.ViewRootImpl;

import java.util.ArrayList;
import java.util.List;

public class MyTraversalRunnable implements Runnable {
  private ViewRootImpl $$viewRootImpl;
  private Runnable originTraversals;
  private View rootView;
  private List<ViewTraversalsListener> listeners = new ArrayList<>();

  public MyTraversalRunnable(Runnable originTraversals, View rootView) {
    $$viewRootImpl = (ViewRootImpl) rootView.getRootView().getParent();
    this.originTraversals = originTraversals;
    this.rootView = rootView;
  }

  @Override
  public void run() {
    Log.d("TAG", "run: "+$$viewRootImpl);
    notifyBeforeRun();
    originTraversals.run();
    notifyAfterRun();
  }

  private void notifyBeforeRun() {
    for (ViewTraversalsListener listener : listeners) {
      listener.onBeforeTraversal(rootView);
    }
  }

  private void notifyAfterRun() {
    for (ViewTraversalsListener listener : listeners) {
      listener.onAfterTraversal(rootView);
    }
  }


  public void addTraversalsListener(ViewTraversalsListener listener) {
    listeners.add(listener);
  }

  public void removeTraversalsListener(ViewTraversalsListener listener) {
    listeners.remove(listener);
  }
}
