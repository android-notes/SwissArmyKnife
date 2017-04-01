package com.wanjian.sak;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

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

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by wanjian on 2017/3/7.
 */

class Manager {

    private Config mConfig;

    private SAKCoverView mCoverView;

    private int mStartLayer = 3;
    private int mEndLayer = 30;
    private Canvas mCanvas;
    private Bitmap mInfo;
    private WeakReference<Activity> mCurActRef;
    private ViewTreeObserver.OnPreDrawListener mDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            Log.d("SAK", "draw.....");
            View root = mCoverView.getRootView();
//            root.getViewTreeObserver().removeOnPreDrawListener(this);
            List<AbsLayer> layers = mConfig.getLayers();
            mInfo.eraseColor(0);
            for (AbsLayer layer : layers) {
                layer.draw(mCanvas, root, mStartLayer, mEndLayer);
            }
            mCoverView.setInfo(mInfo);
//            root.getViewTreeObserver().addOnPreDrawListener(this);
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
    }


    void detach(Activity activity) {
        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
        dectorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);
        dectorView.removeView(mCoverView);
    }

    void attach(Activity activity) {
        mCurActRef = new WeakReference<>(activity);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
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

        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
        dectorView.addView(mCoverView);
        dectorView.getViewTreeObserver().addOnPreDrawListener(mDrawListener);
    }

    void unInstall() {
        if (mCurActRef == null) {
            return;
        }
        Activity activity = mCurActRef.get();
        if (activity == null) {
            return;
        }
        ViewGroup decorView = ((ViewGroup) activity.getWindow().getDecorView());

        decorView.getViewTreeObserver().removeOnPreDrawListener(mDrawListener);

        decorView.removeView(mCoverView);
    }
}
