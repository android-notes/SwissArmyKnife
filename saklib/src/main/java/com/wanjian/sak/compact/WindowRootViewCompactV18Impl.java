package com.wanjian.sak.compact;


import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class WindowRootViewCompactV18Impl extends WindowRootViewCompactV16Impl {

    private Field mViewsField;
    private Object mWindowManagerGlobal;
    private List<IWindowChangeListener> changeListeners = new ArrayList<>();
    private View[] lastViews;

    WindowRootViewCompactV18Impl() {
        super(null);
    }

    @Override
    void init() {
        try {
            Class wmClz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmClz.getDeclaredMethod("getInstance");
            mWindowManagerGlobal = getInstanceMethod.invoke(wmClz);
            mViewsField = wmClz.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            lastViews = (View[]) mViewsField.get(mWindowManagerGlobal);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void check() {
        try {
            View[] curViews = (View[]) mViewsField.get(mWindowManagerGlobal);
            if (curViews != lastViews) {
                notifyWindowChange(lastViews, curViews, changeListeners);
                lastViews = curViews;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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
}