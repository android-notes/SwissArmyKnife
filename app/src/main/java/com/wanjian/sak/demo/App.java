package com.wanjian.sak.demo;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wanjian.sak.SAK;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.converter.SizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.layer.BitmapWidthHeightLayer;
import com.wanjian.sak.layer.BorderLayer;
import com.wanjian.sak.layer.InfoLayer;
import com.wanjian.sak.layer.ViewClassLayer;
import com.wanjian.sak.layerview.TakeColorView;
import com.wanjian.sak.mess.Size;

/**
 * Created by wanjian on 2017/3/8.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Config config = new Config.Build(this)
//                .viewFilter(new ViewFilter() {
//                    @Override
//                    public boolean apply(View view) {
//                        if (view instanceof ImageView) {
//                            return true;
//                        }
//                        if (view instanceof LinearLayout) {
//                            return true;
//                        }
//                        return false;
//                    }
//                })
//                .addLayerView(new TakeColorView(this))
//                .addLayer(new InfoLayer(this))
//                .addLayer(new BorderLayer(this))
//                .addLayer(new BitmapWidthHeightLayer(this))
//                .addLayer(new ViewClassLayer(this))
//                .addSizeConverter(new SizeConverter() {
//                    @Override
//                    public String desc() {
//                        return "my converter";
//                    }
//
//                    @Override
//                    public Size convert(Context context, float length) {
//                        return Size.obtain().setLength(length / 2).setUnit("myU");
//                    }
//                })
//                .build();
//        SAK.init(this, config);
        SAK.init(this);
    }
}
