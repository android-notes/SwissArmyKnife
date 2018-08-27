package com.wanjian.sak.layerview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wanjian.sak.R;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.view.SAKCoverView;
import com.wanjian.sak.view.ViewEditPanel;

public class ViewEditView extends AbsLayerView {
    public ViewEditView(Context context) {
        super(context);
        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_edit_view);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (editPanel == null) {
            return;
        }
        if (enabled == false) {
            if (editPanel.getParent() != null) {
                ((ViewGroup) editPanel.getParent()).removeView(editPanel);
            }
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        editPanel = null;
    }

    private int downX, downY, curX, curY;

    private int scaledTouchSlop;
    private View editPanel;

    protected int pressDelay() {
        return 600;
    }

    private android.os.Handler handler = new android.os.Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (Math.abs(downX - curX) < scaledTouchSlop && Math.abs(downY - curY) < scaledTouchSlop) {
                if (editPanel != null && editPanel.getParent() != null) {
                    ((ViewGroup) editPanel.getParent()).removeView(editPanel);
                }
                View pressView = findPressView((downX + curX) / 2, (downY + curY) / 2);
                targetView = null;
                editPanel = getEditView(pressView);
                View view = getRootView();
                if (view instanceof FrameLayout) {
                    ((FrameLayout) view).addView(editPanel);
                }
                editPanel.setVisibility(VISIBLE);
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        curX = (int) event.getRawX();
        curY = (int) event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) event.getRawX();
            downY = (int) event.getRawY();
            handler.postDelayed(runnable, pressDelay());
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            handler.removeCallbacks(runnable);
        }
        View rootView = getRootView();
        ViewGroup decorView = ((ViewGroup) rootView);

        for (int i = decorView.getChildCount() - 1; i > -1; i--) {
            View child = decorView.getChildAt(i);
            if (child instanceof SAKCoverView || child.getVisibility() != VISIBLE) {
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

    private View targetView;
    private int[] location = new int[2];

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

    protected View getEditView(View view) {
        ViewEditPanel editPanel = new ViewEditPanel(getContext());
        editPanel.attachTargetView(view);
        return editPanel;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable);
    }
}
