package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.AbsLayer;
import com.wanjian.sak.Check;
import com.wanjian.sak.DefaultViewFilter;
import com.wanjian.sak.AbsLayer;
import com.wanjian.sak.ViewFilter;
import com.wanjian.sak.canvasimpl.BitmapWidthHeightLayer;
import com.wanjian.sak.canvasimpl.BorderLayer;
import com.wanjian.sak.canvasimpl.ForceBitmapWidthHeightLayer;
import com.wanjian.sak.canvasimpl.InfoLayer;
import com.wanjian.sak.canvasimpl.MarginLayer;
import com.wanjian.sak.canvasimpl.PaddingLayer;
import com.wanjian.sak.canvasimpl.TextColorLayer;
import com.wanjian.sak.canvasimpl.TextSizeLayer;
import com.wanjian.sak.canvasimpl.WidthHeightLayer;
import com.wanjian.sak.layerview.LayerView;
import com.wanjian.sak.view.CornerMeasureView;
import com.wanjian.sak.view.HorizontalMeasureView;
import com.wanjian.sak.view.TakeColorView;
import com.wanjian.sak.view.TreeView;
import com.wanjian.sak.view.VerticalMeasureView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Config {

    private Context mContext;
    private List<AbsLayer> mLayers = new ArrayList<>();
    private List<LayerView> mLayerViews = new ArrayList<>();

    private Config(Build build) {
        mContext = build.mContext;


        mLayers.addAll(build.mDefaultLayers);
        mLayers.addAll(build.mCustomerLayers);


//        mLayerViews.addAll(build.mDefaultLayerViews);
//        mLayerViews.addAll(build.mCustomerLayerViews);

        ViewFilter.FILTER = build.mViewFilter;
        SizeConverter.CONVERTER = build.mSizeConverter;
    }


    public List<AbsLayer> getLayers() {
        return mLayers;
    }

    public List<LayerView> getLayerViews() {
        return mLayerViews;
    }


    public static class Build {
        Context mContext;
        SizeConverter mSizeConverter;
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

            mViewFilter = ViewFilter.FILTER;
            mSizeConverter = SizeConverter.CONVERTER;
        }

        public Build sizeConverter(SizeConverter sizeConverter) {
            Check.isNull(sizeConverter, "size");
            mSizeConverter = sizeConverter;
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
