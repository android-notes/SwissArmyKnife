package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.config.SizeConverter;
import com.wanjian.sak.layerview.LayerView;
import com.wanjian.sak.view.DrawingBoardView;
import com.wanjian.sak.view.SAKCoverView;
import com.wanjian.sak.view.WheelView;

import java.util.List;

/**
 * Created by wanjian on 2017/3/7.
 */

public class Manager {

    private Config mConfig;

    //    private Context mContext;
    private SAKCoverView mCoverView;

    private int mStartLayer = 5;
    private int mEndLayer = 30;

    public Manager(Context context, Config config) {
//        Context mContext = context.getApplicationContext();
        mConfig = config;
        mCoverView = new SAKCoverView(context.getApplicationContext());
        final List<AbsLayer> layers = config.getLayers();

        for (AbsLayer layer : layers) {
            mCoverView.addItem(new ItemLayerLayout(layer));
        }

        List<LayerView> layerViews = config.getLayerViews();
        for (LayerView layerView : layerViews) {
            mCoverView.addItem(new ItemLayerViewLayout(layerView));
        }
        for (SizeConverter converter : config.getSizeConverters()) {
            mCoverView.addItem(new UnitLayout(converter));
        }
        initCoverView();
    }

    private void initCoverView() {
        mCoverView.setOnDrawListener(new DrawingBoardView.OnDrawListener() {
            @Override
            public void onDraw(Canvas canvas) {
                View root = mCoverView.getRootView();
                List<AbsLayer> layers = mConfig.getLayers();
                for (AbsLayer layer : layers) {
                    layer.draw(canvas, root, mStartLayer, mEndLayer);
                }
            }
        });
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
        mCoverView.setOnCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AbsLayer layer : mConfig.getLayers()) {
                    layer.enable(false);
                }
            }
        });

    }

//    public void refresh(Canvas canvas) {
//        List<AbsLayer> layers = mConfig.getLayers();
//
//        for (AbsLayer layer : layers) {
////            layer.draw(canvas, );
//        }
//    }
//
//    public void regist(Activity activity) {
//        attach(activity);
//    }
//
//
//    public void unRegist() {
//    }

    public void detach(Activity activity) {
        mCoverView.detach(activity);
        ((ViewGroup) activity.getWindow().getDecorView()).removeView(mCoverView);
    }

    public void attach(Activity activity) {
        if (mCoverView.getParent() != null) {
            ((ViewGroup) mCoverView.getParent()).removeView(mCoverView);
        }

        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
        dectorView.addView(mCoverView);
        mCoverView.attach(activity);
    }
}
