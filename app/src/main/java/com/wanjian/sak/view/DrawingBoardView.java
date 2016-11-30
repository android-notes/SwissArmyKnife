package com.wanjian.sak.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.CanvasManager;


/**
 * Created by wanjian on 2016/10/23.
 */

public class DrawingBoardView extends View {
    public DrawingBoardView(Context context) {
        super(context);
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public DrawingBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CanvasManager.getInstance(getContext()).draw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (CanvasManager.getInstance(getContext()).isNeedRefresh()) {
//            getRootView().findViewById(android.R.id.content).dispatchTouchEvent(event);
            ViewGroup viewGroup = (ViewGroup) getRootView();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof ContaierView) {
                    continue;
                }
                view.dispatchTouchEvent(event);
            }
            invalidate();
            return true;
        } else {
            invalidate();
            return super.dispatchTouchEvent(event);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        invalidate();
//        return super.onTouchEvent(event);
//    }
}
