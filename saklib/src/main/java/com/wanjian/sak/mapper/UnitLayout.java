package com.wanjian.sak.mapper;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.SizeConverter;

/**
 * Created by wanjian on 2017/3/9.
 */

public class UnitLayout {
    private SizeConverter mSizeConverter;

    public UnitLayout(SizeConverter sizeConverter) {
        mSizeConverter = sizeConverter;
    }

    public final int getLayoutRes() {
        return getLayout();
    }

    protected int getLayout() {
        return R.layout.sak_unit_radiobutton;
    }

    public void onCreate(View view) {
        if (mSizeConverter.getClass()==SizeConverter.CONVERTER.getClass()){
            ((RadioButton) view).setChecked(true);
        }
        ((RadioButton) view).setText(mSizeConverter.desc());
        ((RadioButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SizeConverter.CONVERTER = mSizeConverter;
                }
            }
        });

    }
}
