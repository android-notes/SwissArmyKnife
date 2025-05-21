package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.os.Build;
import android.view.ViewRootImpl;

public abstract class CanvasCompact {

    protected ViewRootImpl viewRootImpl;

    CanvasCompact(ViewRootImpl viewRootImpl) {
        this.viewRootImpl = viewRootImpl;
    }

    public static CanvasCompact get(ViewRootImpl viewRootImpl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new HardwareCanvasV31Impl(viewRootImpl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new HardwareCanvasV29Impl(viewRootImpl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new HardwareCanvasV26Impl(viewRootImpl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new HardwareCanvasV24Impl(viewRootImpl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new HardwareCanvasV23Impl(viewRootImpl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new HardwareCanvasV21Impl(viewRootImpl);
        } else {
            throw new RuntimeException("unsupport android version");
        }
    }

    public abstract Canvas requireCanvas();

    public abstract void releaseCanvas();

}
