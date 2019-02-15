package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layerview.AbsLayerView;

import java.util.List;

public class DashBoardView extends DrawingBoardView {
    private Config config;

    public DashBoardView(@NonNull Context context) {
        this(context, null);
    }

    public DashBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDrawInfo(Canvas canvas) {
        if (config == null) {
            return;
        }
        List<AbsLayer> layers = config.getLayers();
        for (AbsLayer layer : layers) {
            layer.draw(canvas, getRootView());
        }
    }

    public void attachConfig(Config config) {
        this.config = config;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        notifyStateChange();
    }

    public void notifyStateChange() {
        if (config == null) {
            return;
        }
        invalidate();
        for (AbsLayerView layerView : config.getLayerViews()) {
            if (layerView.isEnable() == false) {
                removeView(layerView);
                continue;
            }
            ViewParent parent = layerView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(layerView);
            }
            addView(layerView, layerView.getLayoutParams(generateDefaultLayoutParams()));
        }
    }

}
