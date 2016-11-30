package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
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

    @SuppressLint("NewApi")
    public OperatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public OperatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.sak_operator_layout, this);

    }

}
