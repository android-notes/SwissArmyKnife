package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wanjian.sak.R;
import com.wanjian.sak.view.RootContainerView;
import com.wanjian.sak.support.ViewEditPanel;

public class ViewEditView extends AbsLayer {
    private View mTarget;
    private int[] mLocation = new int[2];

    public ViewEditView(Context context) {
        super(context);
        init();
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_edit_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_edit_view);
    }

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View target = findPressView((int) e.getRawX(), (int) e.getRawY());
                final ViewEditPanel editPanel = new ViewEditPanel(new ContextThemeWrapper(getContext(), R.style.SAK_Theme));
                editPanel.setSizeConverter(getSizeConverter());
                editPanel.attachTargetView(target);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.format = PixelFormat.RGBA_8888;
                showWindow(editPanel, params);
                editPanel.setOnConfirmClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeWindow(editPanel);
                    }
                });
            }
        });
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
        mTarget = getRootView();
        traversal(mTarget, x, y);
        return mTarget;
    }

    private void traversal(View view, int x, int y) {
        if (getViewFilter().filter(view) == false) {
            return;
        }
        if (view.getVisibility() != VISIBLE) {
            return;

        }
        if (inRange(view, x, y) == false) {
            return;
        }

        mTarget = view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                traversal(child, x, y);
            }
        }
    }

    private boolean inRange(View view, int x, int y) {
        view.getLocationOnScreen(mLocation);
        return (mLocation[0] <= x
                && mLocation[1] <= y
                && mLocation[0] + view.getWidth() >= x
                && mLocation[1] + view.getHeight() >= y);
    }

    @Override
    public void onAttached(View view) {
//        Toast.makeText(getContext(), "长按编辑控件", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDetached(View view) {
        mTarget = null;
    }
}
