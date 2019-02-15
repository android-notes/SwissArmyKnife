package com.wanjian.sak.compact;

import android.support.annotation.NonNull;
import android.view.View;

import com.wanjian.sak.proxy.ProxyArrayList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class WindowRootViewCompactV19Impl extends WindowRootViewCompat {

    private List<IWindowChangeListener> changeListeners = new ArrayList<>();
    private ArrayList<View> rootViews;

    WindowRootViewCompactV19Impl() {
        try {
            Class wmClz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmClz.getDeclaredMethod("getInstance");
            Object managerGlobal = getInstanceMethod.invoke(wmClz);
            Field mViewsField = wmClz.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            rootViews = getProxy((ArrayList<View>) mViewsField.get(managerGlobal));
            mViewsField.set(managerGlobal, rootViews);
            mViewsField.setAccessible(false);
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

    private ArrayList<View> getProxy(final ArrayList<View> list) {
        return new ProxyArrayList<View>(list) {
            @Override
            public boolean add(View object) {
                boolean b = super.add(object);
                for (IWindowChangeListener listener : changeListeners) {
                    listener.onAddWindow(object);
                }
                return b;
            }

            @Override
            public View remove(int index) {
                View view = super.remove(index);
                for (IWindowChangeListener listener : changeListeners) {
                    listener.onRemoveWindow(view);
                }
                return view;
            }

            @Override
            public boolean remove(Object object) {
                boolean b = super.remove(object);
                if (object instanceof View) {
                    for (IWindowChangeListener listener : changeListeners) {
                        listener.onRemoveWindow(((View) object));
                    }
                }
                return b;
            }
        };
    }

    @Override
    void onAddWindowChangeListener(@NonNull IWindowChangeListener changeListener) {
        changeListeners.add(changeListener);
        for (View view : rootViews) {
            changeListener.onAddWindow(view);
        }
    }
}