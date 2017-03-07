package com.wanjian.sak;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.layerview.LayerView;
import com.wanjian.sak.view.SAKCoverView;

import java.util.List;

/**
 * Created by wanjian on 2017/3/7.
 */

public class Manager {

    private Config mConfig;

//    private Context mContext;
    private SAKCoverView mCoverView;

    public Manager(Context context, Config config) {
        Context mContext = context.getApplicationContext();
        mConfig = config;
        mCoverView = new SAKCoverView(context);
        List<ILayer> layers = config.getLayers();

        for (ILayer layer : layers) {
            mCoverView.addItem(new ItemLayerLayout(layer));
        }

        List<LayerView> layerViews = config.getLayerViews();
        for (LayerView layerView : layerViews) {
            mCoverView.addItem(new ItemLayerViewLayout(layerView));
        }

    }

    public void refresh(Canvas canvas) {
        List<ILayer> layers = mConfig.getLayers();

        for (ILayer layer : layers) {
//            layer.draw(canvas, );
        }
    }

    public void regist(Activity activity) {
        attach(activity);
    }



    public void unRegist() {
    }

    public void detach(Activity activity) {
        ((ViewGroup) activity.getWindow().getDecorView()).removeView(mCoverView);
    }

    public void attach(Activity activity) {
        if (mCoverView.getParent()!=null){
            ((ViewGroup) mCoverView.getParent()).removeView(mCoverView);
        }

        ViewGroup dectorView = ((ViewGroup) activity.getWindow().getDecorView());
        dectorView.addView(mCoverView);
    }
}
