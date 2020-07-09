package android.view;

import android.graphics.Outline;

public class RenderNode {
  public DisplayListCanvas start(int width, int height) {
    return null;
  }

//  public HardwareCanvas start(int width, int height,Object...obj) {
//    return null;
//  }

  private RenderNode() {
  }

  public static RenderNode create(String name, View owningView) {
    return null;
  }

  public void end(HardwareCanvas canvas) {
  }


  public void end(DisplayListCanvas canvas) {
  }

  public boolean setAlpha(float alpha) {
    return false;
  }


  public boolean setLeftTopRightBottom(int left, int top, int right, int bottom){
    return false;
  }

  public boolean setOutline( Outline outline) {
    return false;
  }

  public boolean setHasOverlappingRendering(boolean hasOverlappingRendering) {
    return false;
  }

  public boolean setClipToBounds(boolean clipToBounds) {
    return false;
  }
  public boolean isValid() {
    return false;
  }

}
