package com.wanjian.sak.mapper;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.layerview.AbsLayerView;

/**
 * Created by wanjian on 2017/2/21.
 */

public class ItemLayerViewLayout {

    private AbsLayerView mLayer;

    public ItemLayerViewLayout(AbsLayerView layer) {
        mLayer = layer;
    }

    public final int getLayoutRes() {
        return getLayout();
    }

    protected int getLayout() {
        return R.layout.sak_layer_item;
    }

    public AbsLayerView getLayerView() {
        return mLayer;
    }


    public void onCreate(final View view) {

        ((TextView) view.findViewById(R.id.desc)).setText(mLayer.description());
        ((CheckBox) view.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLayer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }
}
