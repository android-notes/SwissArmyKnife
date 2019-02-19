package com.wanjian.sak.support;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.wanjian.sak.layer.AbsLayer;


/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class DragLayerView extends AbsLayer {

    private OnTouchListener touchListener;

    public DragLayerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            private float lastRowX;
            private float lastRowY;

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                setTranslationX(getTranslationX() + (e2.getRawX() - lastRowX));
                setTranslationY(getTranslationY() + (e2.getRawY() - lastRowY));
                lastRowX = e2.getRawX();
                lastRowY = e2.getRawY();
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                lastRowX = e.getRawX();
                lastRowY = e.getRawY();
                return true;
            }
        });
        detectorCompat.setIsLongpressEnabled(false);
        super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean b = false;
                if (touchListener != null) {
                    b = touchListener.onTouch(v, event);
                }
                return detectorCompat.onTouchEvent(event) || b;
            }
        });
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        touchListener = l;
    }
}
