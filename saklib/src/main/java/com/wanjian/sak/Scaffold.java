package com.wanjian.sak;

import android.app.Application;
import android.os.Build;
import android.view.InputEvent;
import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.LayerRoot;
import com.wanjian.sak.system.input.InputEventListener;
import com.wanjian.sak.system.input.InputEventProcessorCompact;
import com.wanjian.sak.system.input.InputEventReceiverCompact;
import com.wanjian.sak.system.traversals.ViewTraversalsCompact;
import com.wanjian.sak.system.traversals.ViewTraversalsListener;
import com.wanjian.sak.system.window.compact.IWindowChangeListener;
import com.wanjian.sak.system.window.compact.WindowRootViewCompat;
import com.wanjian.sak.utils.OptionPanelUtils;
import com.wanjian.sak.view.SAKContainerView;

//import com.wanjian.sak.system.canvas.compact.CanvasHolder;

final class Scaffold {
  private static Application sApplication;

  public void install(final Application application, final Config config) {
    sApplication = application;
    WindowRootViewCompat.get(application).addWindowChangeListener(new IWindowChangeListener() {

      @Override
      public void onAddWindow(final ViewRootImpl viewRootImpl, final View view) {
        if (view instanceof SAKContainerView) {
          return;
        }
        view.post(new Runnable() {
          @Override
          public void run() {
            LayerRoot layerRoot = LayerRoot.create(config, viewRootImpl, view, sApplication);
            OptionPanelUtils.enableIfNeeded(layerRoot);
            observerUIChange(config, layerRoot, viewRootImpl, view);
            observerInputEvent(config, layerRoot, viewRootImpl, view);
            OptionPanelUtils.addLayerRoot(layerRoot);
          }
        });
      }

      @Override
      public void onRemoveWindow(ViewRootImpl viewRootImpl, View view) {
//        CanvasHolder.release(viewRootImpl);
      }
    });
    OptionPanelUtils.showEntrance(application, config);
  }


  private void observerInputEvent(Config config, final LayerRoot layerRoot, final ViewRootImpl viewRootImpl, final View rootView) {
    InputEventListener listener = new InputEventListener() {
      @Override
      public boolean onBeforeInputEvent(InputEvent inputEvent) {
        return layerRoot.beforeInputEvent(rootView, inputEvent);
      }

      @Override
      public void onAfterInputEvent(InputEvent inputEvent) {
        layerRoot.afterInputEvent(rootView, inputEvent);
      }
    };
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      InputEventProcessorCompact.get(rootView.getContext(),viewRootImpl, listener);
    } else {
      InputEventReceiverCompact.get(viewRootImpl, listener);
    }
  }

  private void observerUIChange(Config config, final LayerRoot layerRoot, ViewRootImpl viewRootImpl, View view) {
    new ViewTraversalsCompact().register(viewRootImpl, view, new ViewTraversalsListener() {
      @Override
      public void onBeforeTraversal(View rootView) {
        layerRoot.beforeTraversal(rootView);
      }

      @Override
      public void onAfterTraversal(View rootView) {
        layerRoot.afterTraversal(rootView);
      }
    });
  }


//  public void unInstall() {
//
//  }
}
