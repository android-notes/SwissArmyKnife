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

    public AbsLayer(Context context) {
        super(context);
    }

    public void attachConfig(Config config) {
    }

    public abstract String description();

    public abstract Drawable icon();

    public final void enable(boolean enable) {
    }

    protected void onStateChange(boolean isEnable) {

    }

    public final boolean isEnable() {
        return false;
    }

    protected boolean isClipDraw() {
        return false;
    }

    protected int getStartRange() {
        return 0;
    }

    protected int getEndRange() {
        return 0;
    }

    public final void uiUpdate(Canvas canvas, View view) {

    }

    protected void onUiUpdate(Canvas canvas, View rootView) {

    }

    protected void onAttached(View rootView) {
    }


    protected void onDetached(View rootView) {
    }

    protected int[] getLocationAndSize(View view) {
        return null;
    }

    protected int dp2px(int dip) {
        return 0;
    }

    protected int px2dp(float pxValue) {
        return 0;
    }

    protected ISizeConverter getSizeConverter() {
        return null;
    }

    protected ViewFilter getViewFilter() {
        return null;
    }

    protected void showWindow(View view, WindowManager.LayoutParams params) {

    }

    protected void updateWindow(View view, WindowManager.LayoutParams params) {
    }

    protected void removeWindow(View view) {
    }

    public ViewGroup.LayoutParams getLayoutParams(FrameLayout.LayoutParams params) {
        return null;
    }

}
