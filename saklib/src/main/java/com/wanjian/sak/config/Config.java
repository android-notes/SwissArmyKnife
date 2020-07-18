package com.wanjian.sak.config;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.OriginSizeConverter;
import com.wanjian.sak.converter.Px2DpSizeConverter;
import com.wanjian.sak.converter.Px2SpSizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.Layer;
import com.wanjian.sak.utils.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Config {

    private int minRange;
    private int maxRange;
//    private List<AbsLayer> mLayerViews = new ArrayList<>();
    private List<ISizeConverter> mSizeConverterList = new ArrayList<>();
    private int startRange;
    private int endRange;
    private boolean clipDraw;
    List<Item>mLayerList;
    private Config(Build build) {
//        mLayerViews.addAll(build.mDefaultLayerViews);
//        mLayerViews.addAll(build.mCustomerLayerViews);

        ViewFilter.FILTER = build.mViewFilter;
        mSizeConverterList.addAll(build.mSizeConverterList);

        minRange = build.min;
        maxRange = build.max;
        clipDraw = build.clipDraw;
        mLayerList = build.mLayerList;
    }

    public List<Item> getLayerList() {
        return mLayerList;
    }

//    public List<AbsLayer> getLayerViews() {
//        return mLayerViews;
//    }

    public List<ISizeConverter> getSizeConverters() {
        if (mSizeConverterList == null || mSizeConverterList.isEmpty()) {
            return Arrays.<ISizeConverter>asList(new OriginSizeConverter());
        }
        return mSizeConverterList;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public void setEndRange(int endRange) {
        this.endRange = endRange;
    }

    public boolean isClipDraw() {
        return clipDraw;
    }

    public void setClipDraw(boolean clipDraw) {
        this.clipDraw = clipDraw;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public static class Build {
        Context mContext;
        List<ISizeConverter> mSizeConverterList = new ArrayList<>();
        ViewFilter mViewFilter;
        List<Item>mLayerList=new ArrayList<>();
        int min = 0;
        int max = 50;
        boolean clipDraw = true;

        public Build(Context context) {
            Check.isNull(context, "context");
            mContext = context.getApplicationContext();


            mSizeConverterList.add(new Px2DpSizeConverter());
            mSizeConverterList.add(new OriginSizeConverter());
            mSizeConverterList.add(new Px2SpSizeConverter());
            mViewFilter = ViewFilter.FILTER;
        }

        public Build addSizeConverter(ISizeConverter sizeConverter) {
            Check.isNull(sizeConverter, "sizeConverter");
            mSizeConverterList.add(sizeConverter);
            return this;
        }

//        public Build addLayerView(AbsLayer layerView) {
//            Check.isNull(layerView, "layerView");
////            mDefaultLayerViews.clear();
////            mCustomerLayerViews.add(layerView);
//            return this;
//        }

        public Build viewFilter(ViewFilter viewFilter) {
            Check.isNull(viewFilter, "viewFilter");
            mViewFilter = viewFilter;
            return this;
        }

        public Build range(int min, int max) {
            if (min < 0) {
                throw new IllegalArgumentException();
            }
            if (max < min) {
                throw new IllegalArgumentException();
            }
            this.min = min;
            this.max = max;
            return this;
        }

        public Build clipDraw(boolean clip) {
            clipDraw = clip;
            return this;
        }

        public Build addLayer(Class<? extends Layer> clz, Drawable icon, String iconName) {
            mLayerList.add(new Item(clz,icon,iconName));
            return this;
        }
        public Config build() {
            return new Config(this);
        }
    }
}
