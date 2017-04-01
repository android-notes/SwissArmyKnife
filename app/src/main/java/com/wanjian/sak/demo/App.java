package com.wanjian.sak.demo;

import android.app.Application;

/**
 * Created by wanjian on 2017/3/8.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//
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
//                .addLayer(new LayerTxtAdapter(this) {
//                    @Override
//                    protected String getTxt(View view) {
//                        return convertSize(view.getWidth()).getLength()
//                                * convertSize(view.getHeight()).getLength() + "";
//                    }
//
//                    @Override
//                    public String description() {
//                        return "面积";
//                    }
//                })
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

//
//        SAK.init(this);
    }
}
