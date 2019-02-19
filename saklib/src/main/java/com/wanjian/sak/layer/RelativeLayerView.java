package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.Size;
import com.wanjian.sak.view.RootContainerView;

public class RelativeLayerView extends AbsLayer {
    private int mTxtSize;
    private Paint mPaint;
    private int[] mLocation1 = new int[2];
    private int[] mLocation2 = new int[2];
    private View mFirstView;
    private View mSecondView;
    private View mTargetView;
    private int mLocation[] = new int[2];


    public RelativeLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtSize = dp2px(10);
        mPaint.setTextSize(mTxtSize);
        mPaint.setStrokeWidth(dp2px(1));
        init();
    }

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View mTargetView = findPressView((int) e.getRawX(), (int) e.getRawY());
                if (mFirstView != null && mSecondView != null) {
                    mFirstView = mSecondView;
                    mSecondView = null;
                }
                if (mFirstView == null) {
                    mFirstView = mTargetView;
                } else {
                    mSecondView = mTargetView;
                }
                invalidate();
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
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_relative_distance_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_relative_distance);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        View root = getRootView();
        canvas.translate(-root.getPaddingLeft(), -root.getPaddingTop());
        if (mFirstView != null) {
            drawBorder(canvas, mFirstView, mLocation1);
        }
        if (mFirstView == mSecondView) {
            return;
        }
        if (mSecondView == null) {
            return;
        }
        drawBorder(canvas, mSecondView, mLocation2);

        mPaint.setStyle(Paint.Style.FILL);
        ISizeConverter sizeConverter = getSizeConverter();
        //2 在 1 左边
        if (mLocation2[0] + mSecondView.getWidth() < mLocation1[0]) {
            canvas.save();
            int x = mLocation2[0] + mSecondView.getWidth();
            int y = mLocation2[1] + mSecondView.getHeight() / 2;
            int length = mLocation1[0] - (mLocation2[0] + mSecondView.getWidth());
            canvas.translate(x, y);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, length, 0, mPaint);
            Size size = sizeConverter.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = mPaint.measureText(txt);
            canvas.translate(length / 2 - txtLength / 2, mTxtSize / 2);
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawText(txt, 0, 0, mPaint);
            canvas.restore();
        }
        //2 在 1 右边
        if (mLocation2[0] > mLocation1[0] + mFirstView.getWidth()) {
            canvas.save();
            int x = mLocation2[0];
            int y = mLocation2[1] + mSecondView.getHeight() / 2;
            int length = mLocation2[0] - (mLocation1[0] + mFirstView.getWidth());
            canvas.translate(x, y);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, -length, 0, mPaint);
            Size size = sizeConverter.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = mPaint.measureText(txt);
            canvas.translate(-length / 2 - txtLength / 2, mTxtSize / 2);
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawText(txt, 0, 0, mPaint);
            canvas.restore();
        }
        //2 在 1 上边
        if (mLocation2[1] + mSecondView.getHeight() < mLocation1[1]) {
            canvas.save();
            int x = mLocation2[0] + mSecondView.getWidth() / 2;
            int y = mLocation2[1] + mSecondView.getHeight();
            int length = mLocation1[1] - (mLocation2[1] + mSecondView.getHeight());
            canvas.translate(x, y);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 0, length, mPaint);
            Size size = sizeConverter.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = mPaint.measureText(txt);
            canvas.translate(-txtLength / 2, length / 2 + mTxtSize / 2);
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawText(txt, 0, 0, mPaint);
            canvas.restore();
        }
        //2 在 1 下边
        if (mLocation2[1] > mLocation1[1] + mFirstView.getHeight()) {
            canvas.save();
            int x = mLocation2[0] + mSecondView.getWidth() / 2;
            int y = mLocation2[1];
            int length = mLocation2[1] - (mLocation1[1] + mFirstView.getHeight());
            canvas.translate(x, y);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 0, -length, mPaint);
            Size size = sizeConverter.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = mPaint.measureText(txt);
            canvas.translate(-txtLength / 2, -length / 2 + mTxtSize / 2);
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawText(txt, 0, 0, mPaint);
            canvas.restore();
        }

    }

    private void drawBorder(Canvas canvas, View view, int[] mLocation) {
        view.getLocationInWindow(mLocation);
        canvas.save();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight(), mPaint);
        canvas.restore();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        invalidate();
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
        mTargetView = getRootView();
        traversal(mTargetView, x, y);
        return mTargetView;
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

        mTargetView = view;
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
        setWillNotDraw(false);
    }


    @Override
    public void onDetached(View view) {
        clean();
    }

    private void clean() {
        mTargetView = mFirstView = mSecondView = null;
    }
}
