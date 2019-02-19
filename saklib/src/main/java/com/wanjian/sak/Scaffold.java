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
import com.wanjian.sak.utils.Check;
import com.wanjian.sak.view.DashBoardView;
import com.wanjian.sak.view.OptPanelView;
import com.wanjian.sak.view.RootContainerView;
import com.wanjian.sak.view.SAKEntranceView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

final class Scaffold {

    public void install(Application application, Config config) {
        if (this.application != null) {
            return;
        }
        init(application, config);
    }

    private OptPanelView optPanelView;
    private Application application;
    private DashBoardView dashBoardView;
    private List<WeakReference<View>> windowRef = new ArrayList<>();
    private IWindowChangeListener windowChangeListener = new IWindowChangeListener() {
        @Override
        public void onAddWindow(View view) {
            windowRef.add(new WeakReference<>(view));
            addContainerView(view);
        }

        @Override
        public void onRemoveWindow(View view) {
            removeRef(view);
            removeContainerView(view);
        }
    };

    private void removeRef(View view) {
        // TODO: 2019/2/19
    }


    private void init(final Application application, Config config) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("init on ui thread !");
        }
        Check.isNull(application, "application");

        this.application = application;
        if (config == null) {
            config = new Config.Build(application).build();
        }

        dashBoardView = new DashBoardView(new ContextThemeWrapper(application, R.style.SAK_Theme));
        dashBoardView.attachConfig(config);

        optPanelView = new OptPanelView(new ContextThemeWrapper(application, R.style.SAK_Theme));
        optPanelView.attachConfig(config);
        optPanelView.setOnConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WindowManager) application.getSystemService(Context.WINDOW_SERVICE)).removeViewImmediate(optPanelView);
                dashBoardView.notifyStateChange();
            }
        });

        for (AbsLayer layerView : config.getLayerViews()) {
            layerView.attachConfig(config);
        }
        WindowRootViewCompat.get(application).addWindowChangeListener(windowChangeListener);
    }


    private void removeContainerView(View view) {
        if (verify(view) == false) {
            return;
        }
        ViewGroup group = (ViewGroup) view;
        for (int i = group.getChildCount() - 1; i > -1; i--) {
            if (group.getChildAt(i) instanceof RootContainerView) {
                group.removeViewAt(i);
            }
        }
    }

    private boolean verify(View view) {
        return (view instanceof FrameLayout) || (view instanceof RelativeLayout);
    }

    private void addContainerView(View view) {
        if (!(view instanceof FrameLayout) && !(view instanceof RelativeLayout)) {
            return;
        }
        final RootContainerView rootContainerView = new RootContainerView(new ContextThemeWrapper(application, R.style.SAK_Theme));
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

    private void activateCurWindow(RootContainerView rootContainerView) {
        ViewParent parent = dashBoardView.getParent();
        if (parent == rootContainerView) {
            return;
        }
        if (parent != null) {
            ((ViewGroup) parent).removeView(dashBoardView);
        }
        rootContainerView.addView(dashBoardView, 0);
    }

    private void showOptPanel(Context ctx) {
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        try {
            manager.addView(optPanelView, params);
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }


    public void unInstall() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("unInstall on ui thread !");
        }
        if (application == null) {
            return;
        }
        WindowRootViewCompat.get(application).removeWindowChangeListener(windowChangeListener);
        for (WeakReference<View> reference : windowRef) {
            View view = reference.get();
            if (view == null || verify(view) == false) {
                continue;
            }
            ViewGroup group = (ViewGroup) view;
            for (int i = group.getChildCount() - 1; i > -1; i--) {
                if (group.getChildAt(i) instanceof RootContainerView) {
                    group.removeViewAt(i);
                }
            }
        }
        windowRef.clear();
        application = null;
    }
}
