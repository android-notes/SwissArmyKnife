package com.wanjian.sak.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wanjian.sak.R;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.LayerRoot;
import com.wanjian.sak.view.OptPanelView;
import com.wanjian.sak.view.SAKContainerView;

public class OptionPanelUtils {


  static OptPanelView optPanelView;

  public static void showEntrance(final Application application, final Config config) {

//    SAKEntranceView entranceView = new SAKEntranceView(application);
    final ImageView entranceView = new ImageView(application);
    entranceView.setImageResource(R.drawable.sak_launcher_icon);
    int size = ScreenUtils.dp2px(application, 40);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);

    final SAKContainerView containerView = new SAKContainerView(application);
    containerView.addView(entranceView, layoutParams);

    final WindowManager windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
    params.format = PixelFormat.RGBA_8888;
    params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    } else {
      params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    }
    windowManager.addView(containerView, params);
    entranceView.setOnTouchListener(new View.OnTouchListener() {
      float lastX;
      float lastY;
      float downX;
      float downY;
      boolean click;

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
          downX = lastX = event.getRawX();
          downY = lastY = event.getRawY();
          click = true;
          return true;
        }
        float curX = event.getRawX();
        float curY = event.getRawY();
        params.x = (int) (params.x + (lastX - curX));
        params.y = (int) (params.y + (curY - lastY));
        lastX = curX;
        lastY = curY;
        if (distance(downX, downY, curX, curY) > ScreenUtils.dp2px(application, 8)) {
          click = false;
        }
        windowManager.updateViewLayout(containerView, params);
        if (event.getActionMasked() == MotionEvent.ACTION_UP && click) {
          entranceView.performClick();
        }
        return false;
      }
    });
    optPanelView = new OptPanelView(application);
    optPanelView.attachConfig(config);
    entranceView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showOptPanel(application, config);
      }
    });
  }

  private static double distance(float downX, float downY, float curX, float curY) {
    return Math.sqrt(Math.pow((downX - curX), 2) + Math.pow((downY - curY), 2));
  }


  private static void showOptPanel(Application application, Config config) {
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    final SAKContainerView containerView = new SAKContainerView(application);
    containerView.addView(optPanelView, layoutParams);

    final WindowManager windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.height = WindowManager.LayoutParams.MATCH_PARENT;
    params.format = PixelFormat.RGBA_8888;
    params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    } else {
      params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    }
    windowManager.addView(containerView, params);

    optPanelView.setConfirmListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        containerView.removeAllViews();
        windowManager.removeView(containerView);
      }
    });
    containerView.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
          optPanelView.performClick();
          return true;
        }
        return false;
      }
    });
  }

  public static void addLayerRoot(LayerRoot layerRoot) {
//    optPanelView.attachConfig();
    optPanelView.add(layerRoot);
  }

  public static void enableIfNeeded(LayerRoot layerRoot) {
    optPanelView.enableIfNeeded(layerRoot);
  }
}
