package com.wanjian.sak;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by wanjian on 2017/2/21.
 */

public class ItemLayerLayout {

    private ILayer mLayer;

    public ItemLayerLayout(ILayer layer) {
        mLayer = layer;
    }

    public final int getLayoutRes() {
        return getLayout();
    }

    protected int getLayout() {
        return R.layout.sak_layer_item;
    }

    public void onCreate(View view) {
        ((TextView) view.findViewById(R.id.desc)).setText(mLayer.description());
        ((CheckBox) view.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLayer.enable(isChecked);
            }
        });
    }
}
