package com.wanjian.sak.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不会复用控件
 */
public class GridScroller extends GridView {
    public GridScroller(Context context) {
        super(context);
    }

    public GridScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
