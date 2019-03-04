package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.filter.ViewFilter;

/**
 * Created by wanjian on 2017/3/9.
 */

public abstract class AbsLayer extends FrameLayout {
    private boolean mEnable;
    private Config config;

    public AbsLayer(Context context) {
        super(context);
    }

    public void attachConfig(Config config) {
        this.config = config;
    }

    public abstract String description();

    public abstract Drawable icon();

    public final void enable(boolean enable) {
        this.mEnable = enable;
        onStateChange(enable);
    }

    protected void onStateChange(boolean isEnable) {

    }

    protected Config getConfig() {
        return config;
    }

    public final boolean isEnable() {
        return mEnable;
    }

    protected boolean isClipDraw() {
        return config.isClipDraw();
    }

    protected int getStartRange() {
        return config.getStartRange();
    }

    protected int getEndRange() {
        return config.getEndRange();
    }

    public final void uiUpdate(Canvas canvas, View view) {
        if (!isEnable()) {
            return;
        }
        int count = canvas.save();
        onUiUpdate(canvas, view);
        canvas.restoreToCount(count);
    }

    protected void onUiUpdate(Canvas canvas, View rootView) {

    }

    @Override
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttached(getRootView());
    }

    protected void onAttached(View rootView) {
    }

    @Override
    protected final void onDetachedFromWindow() {
        onDetached(getRootView());
        super.onDetachedFromWindow();
    }

    protected void onDetached(View rootView) {
    }

    /**
     * @param view view
     * @return x, y, width,height
     */
    protected int[] getLocationAndSize(View view) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        View decorView = view.getRootView();// dialog 内边距
        return new int[]{locations[0] - decorView.getPaddingLeft(), locations[1] - decorView.getPaddingTop(), view.getWidth(), view.getHeight()};
    }

    protected int dp2px(int dip) {
        float density = getRootView().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    protected int px2dp(float pxValue) {
        final float scale = getRootView().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    protected ISizeConverter getSizeConverter() {
        return ISizeConverter.CONVERTER;
    }

    protected ViewFilter getViewFilter() {
        return ViewFilter.FILTER;
    }

    protected void showWindow(View view, WindowManager.LayoutParams params) {
        WindowManager manager = (WindowManager) getRootView().getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.addView(view, params);
    }

    protected void updateWindow(View view, WindowManager.LayoutParams params) {
        WindowManager manager = (WindowManager) getRootView().getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.updateViewLayout(view, params);
    }

    protected void removeWindow(View view) {
        WindowManager manager = (WindowManager) getRootView().getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.removeViewImmediate(view);
    }

    public ViewGroup.LayoutParams getLayoutParams(FrameLayout.LayoutParams params) {
        return params;
    }

}
