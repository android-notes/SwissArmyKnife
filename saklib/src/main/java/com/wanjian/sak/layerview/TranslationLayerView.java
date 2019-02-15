package com.wanjian.sak.layerview;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.view.RootContainerView;

public class TranslationLayerView extends AbsLayerView {
    private View targetView;
    private int[] location = new int[2];

    public TranslationLayerView(Context context) {
        super(context);
        init();
    }


    @Override
    public String description() {
        return "移动控件";
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }

    View target;

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onShowPress(MotionEvent e) {
                super.onShowPress(e);
                target = findPressView((int) e.getRawX(), (int) e.getRawY());
            }

            private float lastRowX;
            private float lastRowY;

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (target == null) {
                    return true;
                }
                target.setTranslationX(target.getTranslationX() + (e2.getRawX() - lastRowX));
                target.setTranslationY(target.getTranslationY() + (e2.getRawY() - lastRowY));
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
                return detectorCompat.onTouchEvent(event);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        int curX = (int) event.getRawX();
        int curY = (int) event.getRawY();
        View rootView = getRootView();
        ViewGroup decorView = ((ViewGroup) rootView);

        for (int i = decorView.getChildCount() - 1; i > -1; i--) {
            View child = decorView.getChildAt(i);
            if (child instanceof RootContainerView || child.getVisibility() != VISIBLE) {
                continue;
            }
            if (inRange(child, curX, curY) == false) {
                continue;
            }
            if (child.dispatchTouchEvent(event)) {
                return true;
            }
        }

        return true;
    }

    protected View findPressView(int x, int y) {
        targetView = getRootView();
        traversal(targetView, x, y);
        return targetView;
    }

    private void traversal(View view, int x, int y) {
        if (ViewFilter.FILTER.filter(view) == false) {
            return;
        }
        if (view.getVisibility() != VISIBLE) {
            return;

        }
        if (inRange(view, x, y) == false) {
            return;
        }

        targetView = view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                traversal(child, x, y);
            }
        }
    }

    private boolean inRange(View view, int x, int y) {
        view.getLocationOnScreen(location);
        return (location[0] <= x
                && location[1] <= y
                && location[0] + view.getWidth() >= x
                && location[1] + view.getHeight() >= y);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
        Toast.makeText(getContext(), "长按编辑控件", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        target = null;
    }
}
