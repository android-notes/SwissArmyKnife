package com.wanjian.sak.system.input;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.SparseIntArray;
import android.view.InputChannel;
import android.view.InputEventReceiver;
import android.view.ViewRootImpl;

import com.wanjian.sak.unsafe.UnsafeProxy;

import java.lang.reflect.Field;

public abstract class InputEventProcessorCompact {
    public static void get(Context context, ViewRootImpl viewRootImpl, InputEventListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new InputEventCompatProcessorV30(context, viewRootImpl, listener);
        }
    }
}
