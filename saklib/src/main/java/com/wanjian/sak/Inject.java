package com.wanjian.sak;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.view.SAKCoverView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.wanjian.sak.SAK.KEY;

/**
 * Created by wanjian on 2018/2/8.
 */

    /*
     * 4.0.3_r1    WindowManagerImpl   private View[] mViews;
     * 4.0.4       WindowManagerImpl   private View[] mViews;
     *
     * 4.1.1       WindowManagerImpl   private View[] mViews;
     * 4.1.2       WindowManagerImpl   private View[] mViews;
     *
     * 4.2_r1      WindowManagerGlobal  private View[] mViews
     * 4.2.2 r1    WindowManagerGlobal  private View[] mViews
     *
     * 4.3_r2.1     WindowManagerGlobal  private View[] mViews;
     *
     * 4.4_r1       WindowManagerGlobal   private final ArrayList<View> mViews
     * 4.4.2_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     *
     * 5.0.0_r2     WindowManagerGlobal   private final ArrayList<View> mViews
     *
     * 6.0.0_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     *
     * 7.0.0_r1     WindowManagerGlobal   private final ArrayList<View> mViews
     *
     * 8.0.0_r4    WindowManagerGlobal   private final ArrayList<View> mViews
     */

class Inject {
    private Manager manager;
    private Handler handler;
    private Context context;
    private ObserveViewFiledTask observeViewFiledTask;

    void install(Application application, Config config) {
        context = application;
        manager = new Manager(application, config);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            WindowManager windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
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
                List<View> views = (List<View>) mViewsFiled.get(managerGlobal);
                for (View view : views) {
                    if (view instanceof FrameLayout) {
                        insertIfNeeded(((FrameLayout) view));
                    }
                }
                synchronized (managerGlobal) {
                    mViewsFiled.set(managerGlobal, new ArrayList<View>(views) {
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
                }
                mViewsFiled.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unInstall() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Object wm = getOuter(windowManager);
            removeObserverViewsField(wm);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            WindowManagerGlobal managerGlobal = WindowManagerGlobal.getInstance();
            removeObserverViewsField(managerGlobal);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManagerGlobal managerGlobal = WindowManagerGlobal.getInstance();
            try {
                Field mViewsFiled = WindowManagerGlobal.class.getDeclaredField("mViews");
                mViewsFiled.setAccessible(true);
                List<View> views = (List<View>) mViewsFiled.get(managerGlobal);
                for (View view : views) {
                    removeSAKCoverView(view);
                }
                synchronized (managerGlobal) {
                    mViewsFiled.set(managerGlobal, new ArrayList<>(views));
                }
                mViewsFiled.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void removeObserverViewsField(final Object obj) {
        try {
            handler.removeCallbacks(observeViewFiledTask);
            Class clz = obj.getClass();
            final Field field = clz.getDeclaredField("mViews");
            field.setAccessible(true);
            View[] views = (View[]) field.get(obj);
            for (View view : views) {
                removeSAKCoverView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeSAKCoverView(View view) {
        if (view instanceof FrameLayout) {
            ViewGroup viewGroup = ((FrameLayout) view);
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (i < childCount) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof SAKCoverView) {
                    viewGroup.removeView(child);
                }
                i++;
            }
        }
//        Object o = view.getTag(KEY);
//        if (o instanceof ViewTreeObserver.OnPreDrawListener) {
//            view.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener) o);
//        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    class ObserveViewFiledTask implements Runnable {

        private Object obj;
        private Field field;

        ObserveViewFiledTask(Object object) {
            this.obj = object;
            Class clz = obj.getClass();
            try {
                field = clz.getDeclaredField("mViews");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        View[] last = null;
        final int delay = 1000;

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
    }

    private void observerViewsField(final Object obj) {

        try {
            handler = new Handler();
            observeViewFiledTask = new ObserveViewFiledTask(obj);
            Class clz = obj.getClass();
            final Field field = clz.getDeclaredField("mViews");
            field.setAccessible(true);
            synchronized (obj) {
                View[] views = (View[]) field.get(obj);
                for (View view : views) {
                    if (view instanceof FrameLayout) {
                        insertIfNeeded(((FrameLayout) view));
                    }
                }
            }
            handler.post(observeViewFiledTask);
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
                rootView.removeView(child);
            }
        }
        manager.attach(rootView);
    }

}
