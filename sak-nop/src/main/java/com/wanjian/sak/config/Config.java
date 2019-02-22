package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.AbsLayer;

import java.util.List;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Config {


    public List<AbsLayer> getLayerViews() {
        return null;
    }

    public List<ISizeConverter> getSizeConverters() {

        return null;
    }

    public int getStartRange() {
        return 0;
    }

    public void setStartRange(int startRange) {
    }

    public int getEndRange() {
        return 0;
    }

    public void setEndRange(int endRange) {
    }

    public boolean isClipDraw() {
        return false;
    }

    public void setClipDraw(boolean clipDraw) {
    }

    public int getMinRange() {
        return 0;
    }

    public int getMaxRange() {
        return 0;
    }

    public static class Build {

        public Build(Context context) {
        }

        public Build addSizeConverter(ISizeConverter sizeConverter) {
            return this;
        }

        public Build addLayerView(AbsLayer layerView) {
            return this;
        }

        public Build viewFilter(ViewFilter viewFilter) {
            return this;
        }

        public Build range(int min, int max) {

            return this;
        }

        public Build clipDraw(boolean clip) {

            return this;
        }

        public Config build() {
            return null;
        }
    }
}
