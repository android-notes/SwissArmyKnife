package com.wanjian.sak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.wanjian.sak.view.SAKCoverView;
import com.wanjian.sak.view.WheelView;

import java.util.List;

/**
 * Created by wanjian on 2017/3/7.
 */

class Manager {

    private Config mConfig;

    private SAKCoverView mCoverView;

    private int mStartLayer = 0;
    private int mEndLayer = 30;
    private Canvas mCanvas;
    private Bitmap mInfo;
    private boolean mDrawIfOutOfBounds = false;
    private ViewTreeObserver.OnPreDrawListener mDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            View root = mCoverView.getRootView();
            List<AbsLayer> layers = mConfig.getLayers();
            mInfo.eraseColor(0);
            for (AbsLayer layer : layers) {
                layer.drawIfOutBounds(mDrawIfOutOfBounds);
                layer.draw(mCanvas, root, mStartLayer, mEndLayer);
            }
            mCoverView.setInfo(mInfo);
            return true;
        }
    };

    Manager(Context context, Config config) {
        mConfig = config;
        mCoverView = new SAKCoverView(context.getApplicationContext());
        final List<AbsLayer> layers = config.getLayers();

        for (AbsLayer layer : layers) {
            mCoverView.addItem(new ItemLayerLayout(layer));
        }

        List<AbsLayerView> layerViews = config.getLayerViews();
        for (AbsLayerView layerView : layerViews) {
            mCoverView.addItem(new ItemLayerViewLayout(layerView));
        }
        for (SizeConverter converter : config.getSizeConverters()) {
            mCoverView.addItem(new UnitLayout(converter));
        }
        initCoverView();
        mCanvas = new Canvas();
    }

    private void initCoverView() {
        mCoverView.setStartLayerChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                mStartLayer = num;
            }
        });

        mCoverView.setEndLayerChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                mEndLayer = num;
            }
        });

        mCoverView.setDrawIfOutOfBoundsClickListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDrawIfOutOfBounds = isChecked;
            }
        });
    }


    void detach(FrameLayout rootView) {
        ViewGroup dectorView = rootView;
        dectorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);
        dectorView.removeView(mCoverView);
    }

    void attach(FrameLayout rootView) {
        DisplayMetrics metrics = rootView.getResources().getDisplayMetrics();
        if (mInfo == null) {
            mInfo = BitmapCreater.create(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        }
        if (mInfo == null) {
            Log.w("SAK", "out of memory....");
            return;
        }
        //屏幕旋转情况
        if (mInfo.getHeight() != metrics.heightPixels || mInfo.getWidth() != metrics.widthPixels) {
            mInfo = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        }

        if (mInfo == null) {
            Log.w("SAK", "out of memory....");
            return;
        }
        mCanvas.setBitmap(mInfo);
        if (mCoverView.getParent() != null) {
            ((ViewGroup) mCoverView.getParent()).removeView(mCoverView);
        }
        int h = rootView.getHeight() - rootView.getPaddingTop() - rootView.getPaddingBottom();
        int w = rootView.getWidth() - rootView.getPaddingLeft() - rootView.getPaddingRight();
        if (w > 0 && h > 0) {
            mCoverView.setLayoutParams(new FrameLayout.LayoutParams(w, h));
            int max = (int) (metrics.density * 550);
            if (h > max) {
                h = max;
            }
            mCoverView.findViewById(R.id.optContainer).getLayoutParams().height = h;

        }
        ViewGroup dectorView = rootView;
        dectorView.addView(mCoverView);
        dectorView.getViewTreeObserver().addOnPreDrawListener(mDrawListener);
    }

    void unInstall(FrameLayout rootView) {
        ViewGroup decorView = rootView;

        decorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);

        decorView.removeView(mCoverView);
    }
}
