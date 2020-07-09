package com.wanjian.sak.system.traversals;

import android.view.View;

public interface ViewTraversalsListener {

  void onBeforeTraversal(View rootView);

  void onAfterTraversal(View rootView);
}
