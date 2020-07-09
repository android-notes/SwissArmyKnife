package android.view;

public abstract class ThreadedRenderer extends HardwareRenderer {
  private int mInsetTop, mInsetLeft;
  public void notifyFramePending() {}
}
