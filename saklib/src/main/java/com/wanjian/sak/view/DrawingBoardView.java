package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.wanjian.sak.utils.BitmapCreater;

public abstract class DrawingBoardView extends FrameLayout {
    private Bitmap bitmap;
    private Canvas canvas = new Canvas();

    public DrawingBoardView(@NonNull Context context) {
        this(context, null);
    }

    public DrawingBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                drawInfo(canvas);
                return true;
            }
        });
    }

    private void drawInfo(Canvas canvas) {
        clear();
        onDrawInfo(canvas);
    }

    protected abstract void onDrawInfo(Canvas canvas);

//    @Override
//    protected final void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (bitmap != null && bitmap.isRecycled() == false) {
//            canvas.drawBitmap(bitmap, 0, 0, null);
//        }
//    }


    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null && bitmap.isRecycled() == false) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        super.draw(canvas);
    }

    public void clear() {
        if (bitmap != null && bitmap.isRecycled() == false) {
            bitmap.eraseColor(0);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmap != null && bitmap.getWidth() >= w && bitmap.getHeight() >= h) {
            return;
        }
        bitmap = BitmapCreater.create(w, h, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
    }
}
