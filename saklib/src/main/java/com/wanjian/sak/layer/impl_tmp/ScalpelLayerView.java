package com.wanjian.sak.layer.impl_tmp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.wanjian.sak.R;
import com.wanjian.sak.support.ScalpelFrameLayout;

public class ScalpelLayerView extends ScalpelFrameLayout {

    public ScalpelLayerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.BLACK);
        setLayerInteractionEnabled(true);
        final View optView = LayoutInflater.from(context).inflate(R.layout.sak_scalpel_opt_view, this, false);
        addView(optView);
        optView.setOnTouchListener(new OnTouchListener() {
            private float downX, downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouched(event);
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        downY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float cx = event.getRawX();
                        float cy = event.getRawY();
                        double radius = Math.sqrt((cx - downX) * (cx - downX) + (cy - downY) * (cy - downY));
                        float maxRadius = dp2px(25);
                        if (radius < maxRadius) {
                            optView.setTranslationX(cx - downX);
                            optView.setTranslationY(cy - downY);
                        } else {
                            double angle = Math.atan((cy - downY) / (cx - downX));
                            float y = (float) Math.abs((maxRadius * Math.sin(angle)));
                            float x = (float) Math.abs((maxRadius * Math.cos(angle)));
                            optView.setTranslationX(cx > downX ? x : -x);
                            optView.setTranslationY(cy > downY ? y : -y);
                        }
                        break;
                    default:
                        ObjectAnimator animator = ObjectAnimator.ofFloat(optView, "translationX", optView.getTranslationX(), 0);
                        animator.setInterpolator(new BounceInterpolator());
                        animator.setDuration(300);
                        animator.start();
                        animator = ObjectAnimator.ofFloat(optView, "translationY", optView.getTranslationY(), 0);
                        animator.setInterpolator(new BounceInterpolator());
                        animator.setDuration(300);
                        animator.start();

                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onUiUpdate(Canvas canvas, View rootView) {
        super.onUiUpdate(canvas, rootView);
        invalidate();// TODO: 2019/2/18
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public String description() {
        return "Scalpel";
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_layer_icon);
    }

    @Override
    protected void onAttached(View rootView) {
        super.onAttached(rootView);
    }

    @Override
    protected void onDetached(View rootView) {
        super.onDetached(rootView);
    }
}
