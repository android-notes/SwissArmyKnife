package com.wanjian.sak.layerview;

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
import android.widget.Toast;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.filter.ViewFilter;
import com.wanjian.sak.mess.Size;
import com.wanjian.sak.view.RootContainerView;

public class RelativeLayerView extends AbsLayerView {
    private int txtSize;
    private Paint paint;
    private int[] location1 = new int[2];
    private int[] location2 = new int[2];
    private View firstView;
    private View secondView;
    private View targetView;
    private int location[] = new int[2];


    public RelativeLayerView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtSize = dp2px(12);
        paint.setTextSize(txtSize);
        init();
    }

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View targetView = findPressView((int) e.getRawX(), (int) e.getRawY());
                if (firstView != null && secondView != null) {
                    firstView = secondView;
                    secondView = null;
                }
                if (firstView == null) {
                    firstView = targetView;
                } else {
                    secondView = targetView;
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
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        View root = getRootView();
        canvas.translate(-root.getPaddingLeft(), -root.getPaddingTop());
        if (firstView != null) {
            drawBorder(canvas, firstView, location1);
        }
        if (firstView == secondView) {
            return;
        }
        if (secondView == null) {
            return;
        }
        drawBorder(canvas, secondView, location2);

        paint.setStyle(Paint.Style.FILL);
        //2 在 1 左边
        if (location2[0] + secondView.getWidth() < location1[0]) {
            canvas.save();
            int x = location2[0] + secondView.getWidth();
            int y = location2[1] + secondView.getHeight() / 2;
            int length = location1[0] - (location2[0] + secondView.getWidth());
            canvas.translate(x, y);
            canvas.drawLine(0, 0, length, 0, paint);
            Size size = ISizeConverter.CONVERTER.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = paint.measureText(txt);
            canvas.translate(length / 2 - txtLength / 2, txtSize / 2);
            paint.setColor(0xffffffff);
            canvas.drawRect(0, -txtSize, txtLength, 0, paint);
            paint.setColor(0xff000000);
            canvas.drawText(txt, 0, 0, paint);
            canvas.restore();
        }
        //2 在 1 右边
        if (location2[0] > location1[0] + firstView.getWidth()) {
            canvas.save();
            int x = location2[0];
            int y = location2[1] + secondView.getHeight() / 2;
            int length = location2[0] - (location1[0] + firstView.getWidth());
            canvas.translate(x, y);
            canvas.drawLine(0, 0, -length, 0, paint);
            Size size = ISizeConverter.CONVERTER.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = paint.measureText(txt);
            canvas.translate(-length / 2 - txtLength / 2, txtSize / 2);
            paint.setColor(0xffffffff);
            canvas.drawRect(0, -txtSize, txtLength, 0, paint);
            paint.setColor(0xff000000);
            canvas.drawText(txt, 0, 0, paint);
            canvas.restore();
        }
        //2 在 1 上边
        if (location2[1] + secondView.getHeight() < location1[1]) {
            canvas.save();
            int x = location2[0] + secondView.getWidth() / 2;
            int y = location2[1] + secondView.getHeight();
            int length = location1[1] - (location2[1] + secondView.getHeight());
            canvas.translate(x, y);
            canvas.drawLine(0, 0, 0, length, paint);
            Size size = ISizeConverter.CONVERTER.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = paint.measureText(txt);
            canvas.translate(-txtLength / 2, length / 2 + txtSize / 2);
            paint.setColor(0xffffffff);
            canvas.drawRect(0, -txtSize, txtLength, 0, paint);
            paint.setColor(0xff000000);
            canvas.drawText(txt, 0, 0, paint);
            canvas.restore();
        }
        //2 在 1 下边
        if (location2[1] > location1[1] + firstView.getHeight()) {
            canvas.save();
            int x = location2[0] + secondView.getWidth() / 2;
            int y = location2[1];
            int length = location2[1] - (location1[1] + firstView.getHeight());
            canvas.translate(x, y);
            canvas.drawLine(0, 0, 0, -length, paint);
            Size size = ISizeConverter.CONVERTER.convert(getContext(), length);
            String txt = String.valueOf(size.getLength());
            float txtLength = paint.measureText(txt);
            canvas.translate(-txtLength / 2, -length / 2 + txtSize / 2);
            paint.setColor(0xffffffff);
            canvas.drawRect(0, -txtSize, txtLength, 0, paint);
            paint.setColor(0xff000000);
            canvas.drawText(txt, 0, 0, paint);
            canvas.restore();
        }

    }

    private void drawBorder(Canvas canvas, View view, int[] location) {
        view.getLocationInWindow(location);

        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(150, 245, 212, 217));
        canvas.drawRect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight(), paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight(), paint);
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
        Toast.makeText(getContext(), "长按", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clean();
    }

    private void clean() {
        targetView = firstView = secondView = null;
    }
}
