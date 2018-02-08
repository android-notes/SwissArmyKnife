package com.wanjian.sak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.converter.SizeConverter;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layerview.AbsLayerView;
import com.wanjian.sak.mapper.ItemLayerLayout;
import com.wanjian.sak.mapper.ItemLayerViewLayout;
import com.wanjian.sak.mapper.UnitLayout;
import com.wanjian.sak.utils.BitmapCreater;
import com.wanjian.sak.view.OperatorView;
import com.wanjian.sak.view.SAKCoverView;
import com.wanjian.sak.view.WheelView;

import java.util.List;

/**
 * Created by wanjian on 2017/3/7.
 */

class Manager {

    private Config mConfig;

    private OperatorView mOperatorView;
    private int mStartLayer = 0;
    private int mEndLayer = 30;
    private boolean mDrawIfOutOfBounds = false;

    Manager(Context context, Config config) {
        mConfig = config;
        mOperatorView = new OperatorView(context);
        final List<AbsLayer> layers = config.getLayers();

        for (AbsLayer layer : layers) {
            mOperatorView.addItem(new ItemLayerLayout(layer));
        }

        List<AbsLayerView> layerViews = config.getLayerViews();
        for (AbsLayerView layerView : layerViews) {
            mOperatorView.addItem(new ItemLayerViewLayout(layerView));
        }
        for (SizeConverter converter : config.getSizeConverters()) {
            mOperatorView.addItem(new UnitLayout(converter));
        }
        initOptView();
    }

    private void initOptView() {
        mOperatorView.setStartLayerChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                mStartLayer = num;
            }
        });

        mOperatorView.setEndLayerChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                mEndLayer = num;
            }
        });

        mOperatorView.setDrawIfOutOfBoundsClickListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDrawIfOutOfBounds = isChecked;
            }
        });
    }


    void detach(FrameLayout rootView) {
//        ViewGroup dectorView = rootView;
//        dectorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);
//        dectorView.removeView(mCoverView);
    }

    void attach(FrameLayout rootView) {

        int h = rootView.getHeight() - rootView.getPaddingTop() - rootView.getPaddingBottom();
        int w = rootView.getWidth() - rootView.getPaddingLeft() - rootView.getPaddingRight();

        final SAKCoverView coverView = new SAKCoverView(rootView.getContext());
        coverView.setOnFloatClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewParent parent = mOperatorView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mOperatorView);
                }
                coverView.addView(mOperatorView, 1);
                mOperatorView.show();
            }
        });
        final Bitmap info = BitmapCreater.create(w, h, Bitmap.Config.ARGB_8888);
        if (info == null) {
            Log.w("SAK", "out of memory....");
            return;
        }
        final Canvas canvas = new Canvas(info);

        if (w > 0 && h > 0) {
            coverView.setLayoutParams(new FrameLayout.LayoutParams(w, h));
        }
        rootView.addView(coverView);
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                View root = coverView.getRootView();
                List<AbsLayer> layers = mConfig.getLayers();
                info.eraseColor(0);
                for (AbsLayer layer : layers) {
                    layer.drawIfOutBounds(mDrawIfOutOfBounds);
                    layer.draw(canvas, root, mStartLayer, mEndLayer);
                }
                coverView.setInfo(info);
                return true;
            }
        });
    }

    void unInstall(FrameLayout rootView) {
//        ViewGroup decorView = rootView;
//
//        decorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);
//
//        decorView.removeView(mCoverView);
    }
}
