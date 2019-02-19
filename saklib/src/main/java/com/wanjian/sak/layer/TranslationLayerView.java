package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.view.RootContainerView;


public class TranslationLayerView extends AbsLayer {
    private View mTargetView;
    private int mTxtSize;
    private int[] mLocation = new int[2];
    private Handler mHandler = new Handler();
    private float mLastX;
    private float mLastY;
    private float mDownX;
    private float mDownY;
    private float mTouchSlop;
    private Paint mPaint;
    private ISizeConverter mSizeConverter;
    private Runnable mPending = new Runnable() {
        @Override
        public void run() {
//            TranslationLayerView.super.dispatchTouchEvent();
            mTargetView = findPressView((int) mDownX, (int) mDownY);
            invalidate();
        }
    };


    public TranslationLayerView(Context context) {
        super(context);
        init(context);
    }

    @Override
    public String description() {
        return getResources().getString(R.string.sak_translation_view);
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_drag_icon);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mTxtSize = dp2px(10);
        mPaint.setTextSize(mTxtSize);
        mPaint.setStrokeWidth(dp2px(1));
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTargetView == null) {
            return;
        }
        View parent = (View) mTargetView.getParent();
        parent.getLocationInWindow(mLocation);
        View rootView = getRootView();
        canvas.translate(mLocation[0] - rootView.getPaddingLeft(), mLocation[1] - rootView.getPaddingTop());
        float x = mTargetView.getX();
        float y = mTargetView.getY();
        int w = mTargetView.getWidth();
        int h = mTargetView.getHeight();

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x, y, x + w, y + h, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0, y + h / 2, x, y + h / 2, mPaint);
        canvas.drawLine(x + w / 2, 0, x + w / 2, y, mPaint);
        canvas.drawLine(x + w, y + h / 2, parent.getWidth(), y + h / 2, mPaint);
        canvas.drawLine(x + w / 2, y + h, x + w / 2, parent.getHeight(), mPaint);


        drawLeftTxt(canvas, x, y, h);

        drawTopTxt(canvas, x, y, w);

        drawRightTxt(canvas, parent, x, y, w, h);

        canvas.save();
        String txt = String.valueOf(mSizeConverter.convert(getContext(), parent.getHeight() - y - h).getLength());
        float txtLength = mPaint.measureText(txt);
        canvas.translate(x + (w - txtLength) / 2, (parent.getHeight() - y - h - mTxtSize) / 2 + y + h);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(txt, 0, 0, mPaint);
        canvas.restore();

    }

    private void drawRightTxt(Canvas canvas, View parent, float x, float y, int w, int h) {
        canvas.save();
        String txt = String.valueOf(mSizeConverter.convert(getContext(), parent.getWidth() - x - w).getLength());
        float txtLength = mPaint.measureText(txt);
        canvas.translate((parent.getWidth() - x - w - txtLength) / 2 + x + w, y + (h + mTxtSize) / 2);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(txt, 0, 0, mPaint);
        canvas.restore();
    }

    private void drawTopTxt(Canvas canvas, float x, float y, int w) {
        canvas.save();
        String txt = String.valueOf(mSizeConverter.convert(getContext(), y).getLength());
        float txtLength = mPaint.measureText(txt);
        canvas.translate(x + (w - txtLength) / 2, (y + mTxtSize) / 2);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(txt, 0, 0, mPaint);
        canvas.restore();
    }

    private void drawLeftTxt(Canvas canvas, float x, float y, int h) {
        canvas.save();
        String txt = String.valueOf(mSizeConverter.convert(getContext(), x).getLength());
        float txtLength = mPaint.measureText(txt);
        canvas.translate((x - txtLength) / 2, y + (h + mTxtSize) / 2);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(-2, -mTxtSize - 2, txtLength + 2, 2, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(txt, 0, 0, mPaint);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTargetView = null;
                mDownX = mLastX = event.getRawX();
                mDownY = mLastY = event.getRawY();
                mHandler.postDelayed(mPending, 500);
                break;
            case MotionEvent.ACTION_MOVE:
                float cx = event.getRawX();
                float cy = event.getRawY();
                if (Math.abs(cx - mDownX) > mTouchSlop || Math.abs(cy - mDownY) > mTouchSlop) {
                    mHandler.removeCallbacks(mPending);
                }
                if (mTargetView != null) {
                    float tx = mTargetView.getTranslationX() + (cx - mLastX);
                    float ty = mTargetView.getTranslationY() + (cy - mLastY);
                    mTargetView.setTranslationX(tx);
                    mTargetView.setTranslationY(ty);
                    invalidate();
                }
                mLastX = cx;
                mLastY = cy;
                break;
            default:
                mHandler.removeCallbacks(mPending);
                break;
        }
        if (mTargetView == null) {
            View rootView = getRootView();
            ViewGroup decorView = ((ViewGroup) rootView);
            for (int i = decorView.getChildCount() - 1; i > -1; i--) {
                View child = decorView.getChildAt(i);
                if (child instanceof RootContainerView || child.getVisibility() != VISIBLE) {
                    continue;
                }
                if (inRange(child, (int) event.getRawX(), (int) event.getRawY()) == false) {
                    continue;
                }
                if (child.dispatchTouchEvent(event)) {
                    return true;
                }
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
        mSizeConverter = getSizeConverter();
    }

    public void onDetached(View view) {
        mTargetView = null;
    }
}
