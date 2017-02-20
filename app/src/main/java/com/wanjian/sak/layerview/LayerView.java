package com.wanjian.sak.layerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class LayerView extends View {
    public LayerView(Context context) {
        super(context);
    }


    protected abstract String description();

}
