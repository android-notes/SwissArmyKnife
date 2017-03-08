package com.wanjian.sak;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wanjian.sak.layerview.LayerView;

/**
 * Created by wanjian on 2017/2/21.
 */

public class ItemLayerViewLayout {

    private LayerView mLayer;

    public ItemLayerViewLayout(LayerView layer) {
        mLayer = layer;
    }

    public LayerView getLayerView() {
        return mLayer;
    }


    public void onCreate(final LayerView view) {
//        ((TextView) view.findViewById(R.id.desc)).setText(mLayer.description());
//        ((CheckBox) view.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                view.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//            }
//        });
    }
}
