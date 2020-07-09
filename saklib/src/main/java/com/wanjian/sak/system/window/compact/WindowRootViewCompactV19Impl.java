package com.wanjian.sak.system.window.compact;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.proxy.ProxyArrayList;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class WindowRootViewCompactV19Impl extends WindowRootViewCompat {

    private List<IWindowChangeListener> changeListeners = new ArrayList<>();
    private List<ViewRootImpl> viewRoots;
    private List<View> rootViews;

    WindowRootViewCompactV19Impl() {
        try {
            Class wmClz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmClz.getDeclaredMethod("getInstance");
            Object managerGlobal = getInstanceMethod.invoke(wmClz);

            Field mViewsField = wmClz.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            rootViews = (List<View>) mViewsField.get(managerGlobal);
            mViewsField.set(managerGlobal, rootViews);
            mViewsField.setAccessible(false);

            Field mRootsField = wmClz.getDeclaredField("mRoots");
            mRootsField.setAccessible(true);
            viewRoots = getProxy((ArrayList<ViewRootImpl>) mRootsField.get(managerGlobal));
            mRootsField.set(managerGlobal, viewRoots);
            mRootsField.setAccessible(false);

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

    private ArrayList<ViewRootImpl> getProxy(final ArrayList<ViewRootImpl> list) {
        return new ProxyArrayList<ViewRootImpl>(list) {
            @Override
            public boolean add(ViewRootImpl object) {
                boolean b = super.add(object);
                int index=indexOf(object);
                View view=rootViews.get(index);
                for (IWindowChangeListener listener : changeListeners) {
                    listener.onAddWindow(object,view);
                }
                return b;
            }

            @Override
            public ViewRootImpl remove(int index) {
                ViewRootImpl viewRoot = super.remove(index);
                View view=rootViews.get(index);
                for (IWindowChangeListener listener : changeListeners) {
                    listener.onRemoveWindow(viewRoot,view);
                }
                return viewRoot;
            }

            @Override
            public boolean remove(Object object) {
                int index = indexOf(object);
                View view = rootViews.get(index);
                boolean b = super.remove(object);
                for (IWindowChangeListener listener : changeListeners) {
                    listener.onRemoveWindow(((ViewRootImpl) object), view);
                }
                return b;
            }
        };
    }

    @Override
    void onAddWindowChangeListener(@NonNull IWindowChangeListener changeListener) {
        changeListeners.add(changeListener);
        for (int i = 0; i < viewRoots.size(); i++) {
            ViewRootImpl viewRoot=viewRoots.get(i);
            View view=rootViews.get(i);
            changeListener.onAddWindow(viewRoot,view);
        }

    }
    @Override
    void onRemoveWindowChangeListener(@NonNull IWindowChangeListener changeListener) {
        changeListeners.remove(changeListener);
    }
}