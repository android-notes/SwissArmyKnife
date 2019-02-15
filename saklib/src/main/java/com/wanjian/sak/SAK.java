package com.wanjian.sak;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wanjian.sak.compact.IWindowChangeListener;
import com.wanjian.sak.compact.WindowRootViewCompat;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layerview.AbsLayerView;
import com.wanjian.sak.utils.Check;
import com.wanjian.sak.view.DashBoardView;
import com.wanjian.sak.view.OptPanelView;
import com.wanjian.sak.view.RootContainerView;
import com.wanjian.sak.view.SAKEntranceView;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {
    private static boolean isInstalled = false;
    private static OptPanelView optPanelView;
    private static Context context;
    private static DashBoardView dashBoardView;

    public static void init(Application application) {
        init(application, null);
    }

    public static void init(Application application, Config config) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("init on ui thread !");
        }
        if (application == null) {
            Check.isNull(application, "application");
            return;
        }
        if (isInstalled) {
            return;
        }
        isInstalled = true;
        context = application;
        if (config == null) {
            config = new Config.Build(application).build();
        }

        dashBoardView = new DashBoardView(new ContextThemeWrapper(context, R.style.SAK_Theme));
        dashBoardView.attachConfig(config);


        optPanelView = new OptPanelView(new ContextThemeWrapper(context, R.style.SAK_Theme));
        optPanelView.attachConfig(config);
        optPanelView.setOnConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).removeViewImmediate(optPanelView);
                dashBoardView.notifyStateChange();
            }
        });


        for (AbsLayerView layerView : config.getLayerViews()) {
            layerView.attachConfig(config);
        }
        for (AbsLayer absLayer : config.getLayers()) {
            absLayer.attachConfig(config);
        }


        WindowRootViewCompat.get(application).addWindowChangeListener(new IWindowChangeListener() {
            @Override
            public void onAddWindow(View view) {
                addContainerView(view);
            }

            @Override
            public void onRemoveWindow(View view) {
                removeContainerView(view);
            }
        });
    }

    private static void removeContainerView(View view) {
        if ((view instanceof FrameLayout) == false
                && (view instanceof RelativeLayout) == false) {
            return;
        }
        ViewGroup group = (ViewGroup) view;
        for (int i = group.getChildCount() - 1; i > -1; i--) {
            if (group.getChildAt(i) instanceof RootContainerView) {
                group.removeViewAt(i);
            }
        }
    }

    private static void addContainerView(View view) {
        if ((view instanceof FrameLayout) == false
                && (view instanceof RelativeLayout) == false) {
            return;
        }
        final RootContainerView rootContainerView = new RootContainerView(new ContextThemeWrapper(context, R.style.SAK_Theme));
        ((ViewGroup) view).addView(rootContainerView);
        final Context ctx = view.getContext();
        rootContainerView.setTapListener(new SAKEntranceView.OnTapListener() {
            @Override
            public void onDoubleTap() {
                activateCurWindow(rootContainerView);
                showOptPanel(ctx);
            }

            @Override
            public void onSingleTap() {
                activateCurWindow(rootContainerView);
            }
        });
    }

    private static void activateCurWindow(RootContainerView rootContainerView) {
        ViewParent parent = dashBoardView.getParent();
        if (parent == rootContainerView) {
            return;
        }
        if (parent != null) {
            ((ViewGroup) parent).removeView(dashBoardView);
        }
        rootContainerView.addView(dashBoardView, 0);
    }

    private static void showOptPanel(Context ctx) {
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        try {
            manager.addView(optPanelView, params);
        } catch (WindowManager.BadTokenException e) {

        }
    }

    // TODO: 2019/2/15
    public static void unInstall() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("unInstall on ui thread !");
        }
        if (isInstalled == false) {
            return;
        }
        isInstalled = false;

    }

}
