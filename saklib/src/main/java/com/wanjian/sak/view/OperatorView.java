package com.wanjian.sak.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/10/23.
 */

public class OperatorView extends LinearLayout {
    public OperatorView(Context context) {
        super(context);
        init();
    }

    public OperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private ViewGroup container;

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.sak_operator_layout, this);
        container = (ViewGroup) findViewById(R.id.container);

    }

    public void addItem(View view) {
        container.addView(view);
    }
}
