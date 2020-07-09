package android.view;

import android.os.Looper;

public abstract class InputEventReceiver {

  public InputEventReceiver(InputChannel inputChannel, Looper looper) {

  }
  public void onInputEvent(InputEvent event, int displayId){}
  public void onInputEvent(InputEvent event) {

  }


  public void onBatchedInputEventPending() {

  }


  public void dispose() {

  }


  protected void finalize() throws Throwable {

  }


  public final void finishInputEvent(InputEvent event, boolean handled) {

  }

//  public final boolean consumeBatchedInputEvents(long frameTimeNanos) {
//    return false;
//  }

}
