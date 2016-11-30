package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/10/24.
 */

public class TreeViewOperator extends RelativeLayout {
    public TreeViewOperator(Context context) {
        super(context);
        init();
    }

    public TreeViewOperator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public TreeViewOperator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public TreeViewOperator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.sak_layout_tree_layout, this);
    }
}
