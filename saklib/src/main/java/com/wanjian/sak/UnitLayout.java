package com.wanjian.sak;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjian.sak.config.SizeConverter;

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
        ((RadioButton) view).setText(mSizeConverter.desc());
        ((RadioButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SizeConverter.CONVERTER = mSizeConverter;
            }
        });

    }
}
