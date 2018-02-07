package com.wanjian.sak;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.view.SAKCoverView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wanjian on 2018/2/7.
 */

public class Autopilot extends AbsContentProvider {

    /**
     * //加锁
     * 4.0.3_r1    WindowManagerImpl   private View[] mViews;
     * 4.0.4       WindowManagerImpl   private View[] mViews;
     * <p>
     * 4.1.1       WindowManagerImpl   private View[] mViews;
     * 4.1.2       WindowManagerImpl   private View[] mViews;
     * <p>
     * 4.2_r1      WindowManagerGlobal  private View[] mViews
     * 4.2.2 r1    WindowManagerGlobal  private View[] mViews
     * <p>
     * <p>
     * 4.3_r2.1     WindowManagerGlobal  private View[] mViews;
     * <p>
     * <p>
     * <p>
     * <p>
     * //不加锁
     * 4.4_r1       WindowManagerGlobal   private final ArrayList<View> mViews
     * 4.4.2_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     * <p>
     * <p>
     * 5.0.0_r2     WindowManagerGlobal   private final ArrayList<View> mViews
     * <p>
     * 6.0.0_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     * <p>
     * 7.0.0_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     * <p>
     * 8.0.0_r4    WindowManagerGlobal   private final ArrayList<View> mViews
     */

    @Override
    public boolean onCreate() {
        hook();
        return true;
    }

    private void hook() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Object wm = getOuter(windowManager);
            observerViewsField(wm);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            WindowManagerGlobal managerGlobal = WindowManagerGlobal.getInstance();
            observerViewsField(managerGlobal);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManagerGlobal managerGlobal = WindowManagerGlobal.getInstance();
            try {
                Field mViewsFiled = WindowManagerGlobal.class.getDeclaredField("mViews");
                mViewsFiled.setAccessible(true);
                mViewsFiled.set(managerGlobal, new ArrayList<View>((Collection<? extends View>) mViewsFiled.get(managerGlobal)) {
                    @Override
                    public boolean add(final View view) {
                        boolean b = super.add(view);
                        if (view instanceof FrameLayout) {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    insertIfNeeded((FrameLayout) view);
                                }
                            });
                        }
                        return b;
                    }
                });
                mViewsFiled.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object getOuter(Object innerWM) {

        try {
//            static class CompatModeWrapper implements WindowManager {
//                   private final WindowManagerImpl mWindowManager;
            Field parentFiled = innerWM.getClass().getDeclaredField("mWindowManager");
            parentFiled.setAccessible(true);
            Object outerWM = parentFiled.get(innerWM);
            parentFiled.setAccessible(false);
            return outerWM;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void observerViewsField(final Object obj) {
        final int delay = 1000;
        try {
            Class clz = obj.getClass();
            final Field field = clz.getDeclaredField("mViews");
            field.setAccessible(true);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                View[] last = null;

                @Override
                public void run() {
                    synchronized (obj) {
                        try {
                            View[] views = (View[]) field.get(obj);
                            if (views != last) {
                                last = views;
                                if (views != null && views.length > 0) {
                                    View rootView = views[views.length - 1];
                                    if (rootView instanceof FrameLayout) {
                                        insertIfNeeded(((FrameLayout) rootView));
                                    }
                                }
                            }

                            handler.postDelayed(this, delay);

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertIfNeeded(FrameLayout rootView) {
        int childCount = rootView.getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View child = rootView.getChildAt(i);
            if (child instanceof SAKCoverView) {
                return;
            }
        }

        Manager manager = new Manager(getContext(), new Config.Build(getContext()).build());
        manager.attach(rootView);

    }

}
