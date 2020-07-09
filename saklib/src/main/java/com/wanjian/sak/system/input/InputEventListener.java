package com.wanjian.sak.system.input;

import android.view.InputEvent;

public interface InputEventListener {

  boolean onBeforeInputEvent(InputEvent inputEvent);

  void onAfterInputEvent(InputEvent inputEvent);
}
