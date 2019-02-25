package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.OriginSizeConverter;
import com.wanjian.sak.converter.Px2SpSizeConverter;
import com.wanjian.sak.converter.Px2DpSizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layer.ActivityNameLayerView;
import com.wanjian.sak.layer.BackgroundColorLayer;
import com.wanjian.sak.layer.BitmapWidthHeightLayer;
import com.wanjian.sak.layer.BorderLayer;
import com.wanjian.sak.layer.FragmentNameLayer;
import com.wanjian.sak.layer.GridLayerView;
import com.wanjian.sak.layer.HorizontalMeasureView;
import com.wanjian.sak.layer.InfoLayer;
import com.wanjian.sak.layer.MarginLayer;
import com.wanjian.sak.layer.PaddingLayer;
import com.wanjian.sak.layer.RelativeLayerView;
import com.wanjian.sak.layer.ScalpelLayerView;
import com.wanjian.sak.layer.TakeColorView;
import com.wanjian.sak.layer.TextColorLayer;
import com.wanjian.sak.layer.TextSizeLayer;
import com.wanjian.sak.layer.TranslationLayerView;
import com.wanjian.sak.layer.TreeView;
import com.wanjian.sak.layer.VerticalMeasureView;
import com.wanjian.sak.layer.ViewClassLayer;
import com.wanjian.sak.layer.ViewEditView;
import com.wanjian.sak.layer.WidthHeightLayer;
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
    private List<AbsLayer> mLayerViews = new ArrayList<>();
    private List<ISizeConverter> mSizeConverterList = new ArrayList<>();
    private int startRange;
    private int endRange;
    private boolean clipDraw;

    private Config(Build build) {
        mLayerViews.addAll(build.mDefaultLayerViews);
        mLayerViews.addAll(build.mCustomerLayerViews);

        ViewFilter.FILTER = build.mViewFilter;
        mSizeConverterList.addAll(build.mSizeConverterList);

        minRange = build.min;
        maxRange = build.max;
        clipDraw = build.clipDraw;
    }


    public List<AbsLayer> getLayerViews() {
        return mLayerViews;
    }

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
        List<AbsLayer> mDefaultLayerViews = new ArrayList<>();
        List<AbsLayer> mCustomerLayerViews = new ArrayList<>();
        ViewFilter mViewFilter;
        int min = 0;
        int max = 50;
        boolean clipDraw = true;

        public Build(Context context) {
            Check.isNull(context, "context");
            mContext = context.getApplicationContext();
            mDefaultLayerViews.add(new FragmentNameLayer(mContext));
            mDefaultLayerViews.add(new ActivityNameLayerView(mContext));
            mDefaultLayerViews.add(new ViewEditView(mContext));
            mDefaultLayerViews.add(new RelativeLayerView(mContext));
            mDefaultLayerViews.add(new TranslationLayerView(mContext));
            mDefaultLayerViews.add(new TakeColorView(mContext));
            mDefaultLayerViews.add(new HorizontalMeasureView(mContext));
            mDefaultLayerViews.add(new VerticalMeasureView(mContext));
            mDefaultLayerViews.add(new ScalpelLayerView(mContext));
            mDefaultLayerViews.add(new GridLayerView(mContext));


            mDefaultLayerViews.add(new TextSizeLayer(mContext));
            mDefaultLayerViews.add(new TextColorLayer(mContext));
            mDefaultLayerViews.add(new BorderLayer(mContext));
            mDefaultLayerViews.add(new MarginLayer(mContext));
            mDefaultLayerViews.add(new PaddingLayer(mContext));
            mDefaultLayerViews.add(new WidthHeightLayer(mContext));
            mDefaultLayerViews.add(new BitmapWidthHeightLayer(mContext));
            mDefaultLayerViews.add(new BackgroundColorLayer(mContext));
            mDefaultLayerViews.add(new InfoLayer(mContext));
            mDefaultLayerViews.add(new ViewClassLayer(mContext));

            mDefaultLayerViews.add(new TreeView(mContext));

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

        public Build addLayerView(AbsLayer layerView) {
            Check.isNull(layerView, "layerView");
            mDefaultLayerViews.clear();
            mCustomerLayerViews.add(layerView);
            return this;
        }

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

        public Config build() {
            return new Config(this);
        }
    }
}
