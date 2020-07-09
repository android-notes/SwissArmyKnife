package com.wanjian.sak.layer;

import android.app.Application;
import android.graphics.Canvas;
import android.view.InputEvent;
import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.event.EventListener;
import com.wanjian.sak.system.rendernode.RenderNodeCompact;

public abstract class Layer implements EventListener {
  private ViewRootImpl viewRootImpl;
  private Config config;
  private Application application;
  private LayerRoot layerRoot;
  private RenderNodeCompact renderNode;
  private View rootView;
  private boolean isEnable;

  public Layer() {

  }

  public RenderNodeCompact getRenderNode() {
    return renderNode;
  }

  public final void attach(Config config, ViewRootImpl viewRoot, View rootView, Application application, LayerRoot layerRoot) {
    this.viewRootImpl = viewRoot;
    this.config = config;
    this.application = application;
    this.layerRoot = layerRoot;
    this.rootView = rootView;
    renderNode = RenderNodeCompact.create(getClass().getName());
    onAttach(getRootView());
  }

//
//  private final Canvas requireCanvas() {
//    return CanvasHolder.requireCanvasFor(viewRootImpl);
//  }
//
//  private final void releaseCanvas() {
//    CanvasHolder.releaseCanvasFor(viewRootImpl);
//  }

  protected void invalidate() {
//    renderNode.setLeftTopRightBottom(0, 0, getRootView().getWidth(), getRootView().getHeight());
//    DisplayListCanvas displayListCanvas = renderNode.start(getRootView().getWidth(), getRootView().getHeight());
//    onDraw(displayListCanvas);
//    renderNode.end(displayListCanvas);
    View view = getRootView();
    if (view == null) {
      return;
    }
    Canvas canvas = renderNode.beginRecording(view.getWidth(), view.getHeight());
    draw(canvas);
    renderNode.endRecording(canvas);
    layerRoot.invalidate();
  }

  private void draw(Canvas canvas) {
    if (isEnable == false) {
      canvas.drawColor(0);
      return;
    }
    onDraw(canvas);
  }

  public final boolean isEnable() {
    return isEnable;
  }

  public final void enable(boolean enable) {
    isEnable = enable;
    invalidate();
    onEnableChange(enable);
  }

  protected void onEnableChange(boolean enable) {
  }

  protected void onDraw(Canvas canvas) {

  }

  @Override
  public final void beforeTraversal(View rootView) {
    onBeforeTraversal(rootView);
  }

  @Override
  public final void afterTraversal(View rootView) {
    onAfterTraversal(rootView);
  }

  @Override
  public final boolean beforeInputEvent(View rootView, InputEvent event) {
    //rootView.invalidate();
    return onBeforeInputEvent(rootView, event);
  }

  @Override
  public final void afterInputEvent(View rootView, InputEvent event) {
    //rootView.invalidate();
    onAfterInputEvent(rootView, event);
  }

  protected View getRootView() {
    return rootView;
  }

  protected void onBeforeTraversal(View rootView) {
  }

  protected void onAfterTraversal(View rootView) {
  }

  protected boolean onBeforeInputEvent(View rootView, InputEvent event) {
    return false;
  }

  protected void onAfterInputEvent(View rootView, InputEvent event) {
  }

  protected void onAttach(View rootView) {

  }

  protected Application getContext() {
    return application;
  }
}
