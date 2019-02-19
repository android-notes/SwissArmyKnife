package com.wanjian.sak.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.wanjian.sak.utils.UIHandler;
import com.wanjian.sak.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class WindowRootViewCompactV16Impl extends WindowRootViewCompat {
    private Context mContext;
    private Field mViewsField;
    private List<IWindowChangeListener> changeListeners = new ArrayList<>();
    private View[] lastViews;
    private Object mWindowManger;

    WindowRootViewCompactV16Impl(Context context) {
        mContext = context;
        init();
        loopCheck();
    }

    private static Object getOuter(Object innerWM) {
        try {
            Field parentField = innerWM.getClass().getDeclaredField("mWindowManager");
            parentField.setAccessible(true);
            Object outerWM = parentField.get(innerWM);
            parentField.setAccessible(false);
            return outerWM;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    void init() {
        mWindowManger = getWindowManager();
        mViewsField = getRootViewField();
        lastViews = getRootViews();
    }

    void loopCheck() {
        final int delay = 16;
        UIHandler.get().post(new Runnable() {
            @Override
            public void run() {
                check();
                UIHandler.get().postDelayed(this, delay);
            }
        });
    }

    void check() {
        if (changeListeners == null || changeListeners.isEmpty()) {
            return;
        }
        try {
            View[] curViews = (View[]) mViewsField.get(mWindowManger);
            if (curViews != lastViews) {
                notifyWindowChange(lastViews, curViews, changeListeners);
                lastViews = curViews;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    void notifyWindowChange(View[] lastViews, View[] curViews, List<IWindowChangeListener> changeListeners) {
        View views[] = Utils.diff(lastViews, curViews);
        if (views != null) {
            for (IWindowChangeListener listener : changeListeners) {
                for (View view : views) {
                    listener.onRemoveWindow(view);
                }
            }
        }
        views = Utils.diff(curViews, lastViews);
        if (views != null) {
            for (IWindowChangeListener listener : changeListeners) {
                for (View view : views) {
                    listener.onAddWindow(view);
                }
            }
        }
    }

    @Override
    void onAddWindowChangeListener(@NonNull IWindowChangeListener changeListener) {
        changeListeners.add(changeListener);
        if (lastViews == null) {
            return;
        }
        for (View view : lastViews) {
            changeListener.onAddWindow(view);
        }
    }

    @Override
    void onRemoveWindowChangeListener(@NonNull IWindowChangeListener changeListener) {
        changeListeners.remove(changeListener);
    }

    private Object getWindowManager() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return getOuter(windowManager);
    }

    private Field getRootViewField() {
        try {
            Class clz = mWindowManger.getClass();
            Field field = clz.getDeclaredField("mViews");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private View[] getRootViews() {
        try {
            return (View[]) mViewsField.get(mWindowManger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}