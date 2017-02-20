package com.wanjian.sak.config;

import android.content.Context;
import android.util.ArraySet;

import com.wanjian.sak.AbsLayer;
import com.wanjian.sak.CheckNull;
import com.wanjian.sak.DefaultViewFilter;
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

    protected Context mContext;
    protected SizeConverter mSizeConverter;
    protected List<AbsLayer> mLayers = new ArrayList<>();
    protected List<LayerView> mLayerViews = new ArrayList<>();
    protected Set<ViewFilter> mViewFilters = new HashSet<>();


    private Config(Build build) {
        mContext = build.mContext;
        mSizeConverter = build.mSizeConverter;
        if (mSizeConverter == null) {
            mSizeConverter = new DefaultSizeConverter();
        }
        mLayers.add(new BorderLayer(mContext));
        mLayers.add(new MarginLayer(mContext));
        mLayers.add(new PaddingLayer(mContext));
        mLayers.add(new WidthHeightLayer(mContext));
        mLayers.add(new BitmapWidthHeightLayer(mContext));
        mLayers.add(new TextSizeLayer(mContext));
        mLayers.add(new TextColorLayer(mContext));
        mLayers.add(new ForceBitmapWidthHeightLayer(mContext));
        mLayers.add(new InfoLayer(mContext));

        mLayers.addAll(build.mLayers);


        mLayerViews.add(new HorizontalMeasureView(mContext));
        mLayerViews.add(new VerticalMeasureView(mContext));
        mLayerViews.add(new CornerMeasureView(mContext));
        mLayerViews.add(new TakeColorView(mContext));
        mLayerViews.add(new TreeView(mContext));

        mLayerViews.addAll(build.mLayerViews);


        mViewFilters.add(new DefaultViewFilter());
        mViewFilters.addAll(build.mViewFilters);
    }

    public static class Build {
        Context mContext;
        SizeConverter mSizeConverter;
        List<AbsLayer> mLayers = new ArrayList<>();
        List<LayerView> mLayerViews = new ArrayList<>();
        Set<ViewFilter> mViewFilters = new HashSet<>();

        public Build(Context context) {
            CheckNull.check(context, "context");
            mContext = context;
        }

        public Build size(SizeConverter sizeConverter) {
            CheckNull.check(sizeConverter, "size");
            mSizeConverter = sizeConverter;
            return this;
        }

        public Build addLayer(AbsLayer layer) {
            CheckNull.check(layer, "layer");
            mLayers.add(layer);
            return this;
        }

        public Build addLayerView(LayerView layerView) {
            CheckNull.check(layerView, "layerView");
            mLayerViews.add(layerView);
            return this;
        }

        public Build addViewFilter(ViewFilter viewFilter) {
            CheckNull.check(viewFilter, "viewFilter");
            mViewFilters.add(viewFilter);
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
