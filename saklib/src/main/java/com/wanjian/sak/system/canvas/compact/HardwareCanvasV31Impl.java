package com.wanjian.sak.system.canvas.compact;

import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.view.Choreographer;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewFrameInfo;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HardwareCanvasV31Impl extends HardwareCanvasV29Impl {
    HardwareCanvasV31Impl(ViewRootImpl viewRootImpl) {
        super(viewRootImpl);
    }

    @Override
    protected void markDrawStart(Choreographer choreographer) {
        try {
            ViewFrameInfo frameInfo = getViewFrameInfo();
            frameInfo.markDrawStart();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ViewFrameInfo getViewFrameInfo() {
        try {
            Field mFrameInfoF = ViewRootImpl.class.getDeclaredField("mViewFrameInfo");
            mFrameInfoF.setAccessible(true);
            ViewFrameInfo frameInfo = (ViewFrameInfo) mFrameInfoF.get(viewRootImpl);
            return frameInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FrameInfo getFrameInfo() {
        try {
            Method mFrameInfoM = ViewRootImpl.class.getDeclaredMethod("getUpdatedFrameInfo");
            mFrameInfoM.setAccessible(true);
            FrameInfo frameInfo = (FrameInfo) mFrameInfoM.invoke(viewRootImpl);
            return frameInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void nSyncAndDrawFrame() {
        try {
            ThreadedRenderer renderer = getHardwareRenderer(viewRootImpl);
            Method method = HardwareRenderer.class.getDeclaredMethod("syncAndDrawFrame", FrameInfo.class);
            method.setAccessible(true);
            method.invoke(renderer, getFrameInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
