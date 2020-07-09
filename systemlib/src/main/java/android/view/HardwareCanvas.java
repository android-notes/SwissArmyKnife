package android.view;

import android.graphics.Canvas;

//只有Android 5.0-5.1 才有HardwareCanvas，HardwareCanvas和DisplayListCanvas
// 都是canvas的直接子类，为了编译通过指定HardwareCanvas extends DisplayListCanvas

public class HardwareCanvas extends Canvas {

  public void insertReorderBarrier() {
  }

  public void insertInorderBarrier() {
  }

  public void drawRenderNode(RenderNode renderNode) {
  }
}
