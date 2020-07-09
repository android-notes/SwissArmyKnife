package com.wanjian.sak.layer;

import android.app.Application;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.InputEvent;
import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.config.Item;
import com.wanjian.sak.event.EventListener;
import com.wanjian.sak.system.canvas.compact.CanvasCompact;
import com.wanjian.sak.system.rendernode.RenderNodeCompact;

import java.util.ArrayList;
import java.util.List;

public final class LayerRoot implements EventListener {

  public static LayerRoot create(Config config, ViewRootImpl viewRootImpl, View rootView, Application application) {
    return new LayerRoot(config, viewRootImpl, rootView, application);
  }

  private List<Layer> layers = new ArrayList<>();
  private CanvasCompact canvasCompact;
  private Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case TAG_PENDING_DRAW:
          draw();
          break;
      }
    }
  };

  public List<Layer> getLayers() {
    return layers;
  }

  private void draw() {
    Canvas rootCanvas = requireCanvas();
    if (rootCanvas == null) {
//      handler.sendEmptyMessage(TAG_PENDING_DRAW);
      return;
    }
    if (!rootCanvas.isHardwareAccelerated()) {// TODO: 2020/7/7
      rootCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
      rootView.draw(rootCanvas);
      for (Layer layer : layers) {
        if (layer.isEnable() == false) {
          continue;
        }
        rootCanvas.save();
        layer.onDraw(rootCanvas);
        rootCanvas.restore();
      }
      releaseCanvas();
      return;
    }
    for (Layer layer : layers) {
      if (layer.isEnable() == false) {
        continue;
      }
      RenderNodeCompact renderNode = layer.getRenderNode();
      if (renderNode.isValid()) {
        renderNode.drawRenderNode(rootCanvas);
//        rootCanvas.drawRenderNode(renderNode);
      }
    }
    releaseCanvas();
  }

  private void releaseCanvas() {
    canvasCompact.releaseCanvas();
  }

  private Canvas requireCanvas() {
    if (canvasCompact == null) {
      canvasCompact = CanvasCompact.get(viewRootImpl);
    }
    return canvasCompact.requireCanvas();
  }

  private final int TAG_PENDING_DRAW = 1;
  private ViewRootImpl viewRootImpl;
  private View rootView;

  LayerRoot(Config config, ViewRootImpl viewRootImpl, View rootView, Application application) {
    this.viewRootImpl = viewRootImpl;
    this.rootView = rootView;
    for (Item item : config.getLayerList()) {
      Class<? extends Layer>layerClass=item.layerType;
      Layer layer = null;
      try {
        layer = layerClass.newInstance();
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      }
      layer.attach(config, viewRootImpl, rootView, application, this);
      layers.add(layer);
    }
  }

  protected void invalidate() {
    if (handler.hasMessages(TAG_PENDING_DRAW)) {
      return;
    }
    handler.sendEmptyMessage(TAG_PENDING_DRAW);
  }


  @Override
  public void beforeTraversal(View rootView) {
    for (Layer layer : layers) {
      if (layer.isEnable() == false) {
        continue;
      }
      layer.beforeTraversal(rootView);
    }
  }

  @Override
  public void afterTraversal(View rootView) {
    for (Layer layer : layers) {
      if (layer.isEnable() == false) {
        continue;
      }
      layer.afterTraversal(rootView);
    }
  }

  @Override
  public boolean beforeInputEvent(View rootView, InputEvent event) {
    for (Layer layer : layers) {
      if (layer.isEnable() == false) {
        continue;
      }
      boolean consume = layer.beforeInputEvent(rootView, event);
      if (consume) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void afterInputEvent(View rootView, InputEvent event) {
    for (Layer layer : layers) {
      if (layer.isEnable() == false) {
        continue;
      }
      layer.afterInputEvent(rootView, event);
    }
  }
}
