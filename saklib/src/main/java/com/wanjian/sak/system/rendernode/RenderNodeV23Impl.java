package com.wanjian.sak.system.rendernode;

import android.graphics.Canvas;
import android.view.DisplayListCanvas;
import android.view.HardwareCanvas;
import android.view.RenderNode;
import android.view.ThreadedRenderer;

import java.lang.reflect.Method;

public class RenderNodeV23Impl extends RenderNodeCompact {

  private RenderNode renderNode;

  public RenderNodeV23Impl(String name) {
    renderNode = RenderNode.create(name, null);
  }

  @Override
  public void drawRenderNode(Canvas canvas) {
    ((DisplayListCanvas) canvas).drawRenderNode(renderNode);
  }


  @Override
  public DisplayListCanvas beginRecording(int width, int height) {
    renderNode.setLeftTopRightBottom(0, 0, width, height);
    return renderNode.start(width,height);
  }

  @Override
  public void endRecording(Canvas canvas) {
    renderNode.end((DisplayListCanvas) canvas);
  }

  @Override
  public boolean isValid() {
    return renderNode.isValid();
  }
}
