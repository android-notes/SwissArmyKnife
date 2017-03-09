package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.AbsLayer;
import com.wanjian.sak.utils.Check;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layerimpl.BitmapWidthHeightLayer;
import com.wanjian.sak.layerimpl.BorderLayer;
import com.wanjian.sak.layerimpl.ForceBitmapWidthHeightLayer;
import com.wanjian.sak.layerimpl.InfoLayer;
import com.wanjian.sak.layerimpl.MarginLayer;
import com.wanjian.sak.layerimpl.PaddingLayer;
import com.wanjian.sak.layerimpl.TextColorLayer;
import com.wanjian.sak.layerimpl.TextSizeLayer;
import com.wanjian.sak.layerimpl.WidthHeightLayer;
import com.wanjian.sak.layerview.LayerView;
import com.wanjian.sak.view.CornerMeasureView;
import com.wanjian.sak.view.HorizontalMeasureView;
import com.wanjian.sak.view.TakeColorView;
import com.wanjian.sak.view.TreeView;
import com.wanjian.sak.view.VerticalMeasureView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Config {

    private Context mContext;
    private List<AbsLayer> mLayers = new ArrayList<>();
    private List<LayerView> mLayerViews = new ArrayList<>();
    private List<SizeConverter> mSizeConverterList = new ArrayList<>();

    private Config(Build build) {
        mContext = build.mContext;


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

    public List<LayerView> getLayerViews() {
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
        List<LayerView> mDefaultLayerViews = new ArrayList<>();
        List<LayerView> mCustomerLayerViews = new ArrayList<>();
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
            mDefaultLayers.add(new ForceBitmapWidthHeightLayer(mContext));
            mDefaultLayers.add(new InfoLayer(mContext));

            mDefaultLayerViews.add(new HorizontalMeasureView(mContext));
            mDefaultLayerViews.add(new VerticalMeasureView(mContext));
            mDefaultLayerViews.add(new CornerMeasureView(mContext));
            mDefaultLayerViews.add(new TakeColorView(mContext));
            mDefaultLayerViews.add(new TreeView(mContext));

            mSizeConverterList.add(new Px2dpSizeConverter());
            mSizeConverterList.add(new OriginSizeConverter());
            mSizeConverterList.add(new Px2SpSizeConverter());
            mViewFilter = ViewFilter.FILTER;
        }

        public Build addSizeConverter(SizeConverter sizeConverter) {
            Check.isNull(sizeConverter, "size");
            mSizeConverterList.add(sizeConverter);
            return this;
        }

        public Build addLayer(AbsLayer layer) {
            Check.isNull(layer, "layer");
            mDefaultLayers.clear();
            mCustomerLayers.add(layer);
            return this;
        }

        public Build addLayerView(LayerView layerView) {
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
