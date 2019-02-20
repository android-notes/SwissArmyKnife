package com.wanjian.sak.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.wanjian.sak.R;

public class SAKEntranceView extends ImageView {

    private OnTapListener tapListener;
    private boolean isMoved = false;

    public SAKEntranceView(Context context) {
        this(context, null);
    }

    public SAKEntranceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setImageResource(R.drawable.sak_entrance_icon);
        final GestureDetectorCompat detectorCompat = getGestureDetector();
        detectorCompat.setIsLongpressEnabled(false);
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isMoved) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            translationIfNeeded();
                    }
                }

                return detectorCompat.onTouchEvent(event);
            }

        });
    }

    private GestureDetectorCompat getGestureDetector() {
        return new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            private float lastRowX;
            private float lastRowY;

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                isMoved = true;
                float tx = getTranslationX() + (e2.getRawX() - lastRowX);
                float ty = getTranslationY() + (e2.getRawY() - lastRowY);
                setTranslationX(tx);
                setTranslationY(ty);

                lastRowX = e2.getRawX();
                lastRowY = e2.getRawY();
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                isMoved = false;
                lastRowX = e.getRawX();
                lastRowY = e.getRawY();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (tapListener != null) {
                    tapListener.onDoubleTap();
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (tapListener != null) {
                    tapListener.onSingleTap();
                }
                return true;
            }

        });
    }

    private void translationIfNeeded() {
        isMoved = false;
        float x = getX();
        float y = getY();

        int w = getWidth();
        int h = getHeight();
        float tx = getTranslationX();
        float ty = getTranslationY();
        long duration = 300;
        ViewGroup parent = (ViewGroup) getParent();
        if (x < 0) {
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(this, "translationX", tx, tx - x)
                    .setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        } else if (x + w > parent.getWidth()) {
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(this, "translationX", tx, tx - (x + w - parent.getWidth()))
                    .setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }

        if (y < 0) {
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(this, "translationY", ty, ty - y)
                    .setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        } else if (y + h > parent.getHeight()) {
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(this, "translationY", ty, ty - (y + h - parent.getHeight()))
                    .setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }

    }

    public void setTapListener(OnTapListener tapListener) {
        this.tapListener = tapListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Animator set = AnimatorInflater.loadAnimator(getContext(), R.animator.sak_shake_animator);
        set.setTarget(this);
        set.start();
    }

    public interface OnTapListener {
        void onDoubleTap();

        void onSingleTap();
    }
}
