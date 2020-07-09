package com.wanjian.sak.system.rendernode;

import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;

import java.lang.reflect.Method;

public class RenderNodeV29Impl extends RenderNodeCompact {

  private RenderNode renderNode;

  public RenderNodeV29Impl(String name) {
    renderNode = new RenderNode(name);
  }

  @Override
  public void drawRenderNode(Canvas canvas) {
    ((RecordingCanvas) canvas).drawRenderNode(renderNode);
  }

  @Override
  public RecordingCanvas beginRecording(int width, int height) {
//    renderNode.setLeftTopRightBottom(0, 0, width, height);
    try {
      Method method = RenderNode.class.getDeclaredMethod("setLeftTopRightBottom", int.class, int.class, int.class, int.class);
      method.setAccessible(true);
      method.invoke(renderNode, 0, 0, width, height);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return renderNode.beginRecording();
  }

  @Override
  public void endRecording(Canvas canvas) {
    renderNode.endRecording();
  }

  @Override
  public boolean isValid() {
    return renderNode.hasDisplayList();
  }
}
