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

    private boolean mNeededRefresh;

    private OnDrawListener mOnDrawListener;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOnDrawListener!=null){
            mOnDrawListener.onDraw(canvas);
        }
    }

    public void setOnDrawListener(OnDrawListener drawListener) {
        mOnDrawListener = drawListener;
    }

    public void neededRefresh(boolean refresh) {
        mNeededRefresh = refresh;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mNeededRefresh) {
            ViewGroup viewGroup = (ViewGroup) getRootView();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if ((view instanceof SAKCoverView) || view.getVisibility() != VISIBLE) {
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

   public interface OnDrawListener {
        void onDraw(Canvas canvas);
    }

}
