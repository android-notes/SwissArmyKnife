package com.wanjian.sak.event;

import android.view.InputEvent;
import android.view.View;

public interface EventListener {

  void beforeTraversal(View rootView);

  void afterTraversal(View rootView);

  boolean beforeInputEvent(View rootView, InputEvent event);

  void afterInputEvent(View rootView, InputEvent event);

}
