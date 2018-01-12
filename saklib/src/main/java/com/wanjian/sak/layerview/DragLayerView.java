package com.wanjian.sak.layerview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.wanjian.sak.mess.OnChangeListener;


/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class DragLayerView extends AbsLayerView implements OnChangeListener {


    private float lastX;
    private float lastY;

    public DragLayerView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getRawX();
            lastY = event.getRawY();
        } else {
            float curX = event.getRawX();
            float curY = event.getRawY();
            ViewGroup.LayoutParams params = getLayoutParams();
            if (!(params instanceof ViewGroup.MarginLayoutParams)) {
                return super.onTouchEvent(event);
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
            marginLayoutParams.leftMargin += (curX - lastX);
            marginLayoutParams.topMargin += (curY - lastY);
            requestLayout();
            lastX = curX;
            lastY = curY;

        }
        onChange(event);
        return true;
    }


}
