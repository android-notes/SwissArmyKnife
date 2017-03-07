package com.wanjian.sak.layerview;

import android.content.Context;
import android.view.View;

import com.wanjian.sak.OnChangeListener;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class LayerView extends View  implements OnChangeListener {
    public LayerView(Context context) {
        super(context);
    }


    public abstract String description();

}
