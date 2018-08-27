package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.converter.OriginSizeConverter;
import com.wanjian.sak.converter.Px2SpSizeConverter;
import com.wanjian.sak.converter.Px2dpSizeConverter;
import com.wanjian.sak.converter.SizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layer.ActivityNameLayer;
import com.wanjian.sak.layer.BackgroundColorLayer;
import com.wanjian.sak.layer.BitmapWidthHeightLayer;
import com.wanjian.sak.layer.BorderLayer;
import com.wanjian.sak.layer.ForceBitmapWidthHeightLayer;
import com.wanjian.sak.layer.FragmentNameLayer;
import com.wanjian.sak.layer.InfoLayer;
import com.wanjian.sak.layer.MarginLayer;
import com.wanjian.sak.layer.PaddingLayer;
import com.wanjian.sak.layer.PageDrawPerformanceLayer;
import com.wanjian.sak.layer.TextColorLayer;
import com.wanjian.sak.layer.TextSizeLayer;
import com.wanjian.sak.layer.ViewClassLayer;
import com.wanjian.sak.layer.ViewDrawPerformanceLayer;
import com.wanjian.sak.layer.WidthHeightLayer;
import com.wanjian.sak.layerview.AbsLayerView;
import com.wanjian.sak.layerview.CornerMeasureView;
import com.wanjian.sak.layerview.GridLayerView;
import com.wanjian.sak.layerview.HorizontalMeasureView;
import com.wanjian.sak.layerview.RelativeLayerView;
import com.wanjian.sak.layerview.TakeColorView;
import com.wanjian.sak.layerview.TreeView;
import com.wanjian.sak.layerview.VerticalMeasureView;
import com.wanjian.sak.layerview.ViewEditView;
import com.wanjian.sak.utils.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Config {

    private List<AbsLayer> mLayers = new ArrayList<>();
    private List<AbsLayerView> mLayerViews = new ArrayList<>();
    private List<SizeConverter> mSizeConverterList = new ArrayList<>();

    private Config(Build build) {

        mLayers.addAll(build.mDefaultLayers);
        mLayers.addAll(build.mCustomerLayers);


        mLayerViews.addAll(build.mDefaultLayerViews);
        mLayerViews.addAll(build.mCustomerLayerViews);

        ViewFilter.FILTER = build.mViewFilter;
        mSizeConverterList.addAll(build.mSizeConverterList);
    }


    public List<AbsLayer> getLayers() {
        return mLayers;
    }

    public List<AbsLayerView> getLayerViews() {
        return mLayerViews;
    }

    public List<SizeConverter> getSizeConverters() {
        return mSizeConverterList;
    }

    public static class Build {
        Context mContext;
        List<SizeConverter> mSizeConverterList = new ArrayList<>();
        List<AbsLayer> mDefaultLayers = new ArrayList<>();
        List<AbsLayer> mCustomerLayers = new ArrayList<>();
        List<AbsLayerView> mDefaultLayerViews = new ArrayList<>();
        List<AbsLayerView> mCustomerLayerViews = new ArrayList<>();
        ViewFilter mViewFilter;

        public Build(Context context) {
            Check.isNull(context, "context");
            mContext = context.getApplicationContext();

            mDefaultLayers.add(new BorderLayer(mContext));
            mDefaultLayers.add(new MarginLayer(mContext));
            mDefaultLayers.add(new PaddingLayer(mContext));
            mDefaultLayers.add(new WidthHeightLayer(mContext));
            mDefaultLayers.add(new BitmapWidthHeightLayer(mContext));
            mDefaultLayers.add(new TextSizeLayer(mContext));
            mDefaultLayers.add(new TextColorLayer(mContext));
            mDefaultLayers.add(new BackgroundColorLayer(mContext));
            mDefaultLayers.add(new ForceBitmapWidthHeightLayer(mContext));
            mDefaultLayers.add(new InfoLayer(mContext));
            mDefaultLayers.add(new ViewClassLayer(mContext));
            mDefaultLayers.add(new ActivityNameLayer(mContext));
            mDefaultLayers.add(new FragmentNameLayer(mContext));

            mDefaultLayerViews.add(new ViewEditView(mContext));
            mDefaultLayerViews.add(new GridLayerView(mContext));
            mDefaultLayerViews.add(new RelativeLayerView(mContext));
            mDefaultLayerViews.add(new HorizontalMeasureView(mContext));
            mDefaultLayerViews.add(new VerticalMeasureView(mContext));
            mDefaultLayerViews.add(new CornerMeasureView(mContext));
            mDefaultLayerViews.add(new TakeColorView(mContext));
            mDefaultLayerViews.add(new TreeView(mContext));

            mDefaultLayers.add(new ViewDrawPerformanceLayer(mContext));
            mDefaultLayers.add(new PageDrawPerformanceLayer(mContext));


            mSizeConverterList.add(new Px2dpSizeConverter());
            mSizeConverterList.add(new OriginSizeConverter());
            mSizeConverterList.add(new Px2SpSizeConverter());
            mViewFilter = ViewFilter.FILTER;
        }

        public Build addSizeConverter(SizeConverter sizeConverter) {
            Check.isNull(sizeConverter, "sizeConverter");
            mSizeConverterList.add(sizeConverter);
            return this;
        }

        public Build addLayer(AbsLayer layer) {
            Check.isNull(layer, "absLayer");
            mDefaultLayers.clear();
            mCustomerLayers.add(layer);
            return this;
        }

        public Build addLayerView(AbsLayerView layerView) {
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

        public Config build() {
            return new Config(this);
        }
    }
}
