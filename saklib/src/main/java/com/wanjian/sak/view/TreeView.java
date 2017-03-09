package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanjian.sak.CanvasManager;
import com.wanjian.sak.layerview.LayerView;


/**
 * Created by wanjian on 2016/10/24.
 */

public class TreeView extends LayerView {
    public TreeView(Context context) {
        super(context);
        init();
    }

    @Override
    public String description() {
        return "布局树";
    }



    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dp2px(12));
        tabW = dp2px(20);
        Rect rect = new Rect();
        mPaint.getTextBounds("Aj", 0, 2, rect);
        txtH = rect.height() * 2;
    }



    //    private ViewGroup mViewGroup;
    private int mStartLayer;
    private int mEndLayer;

    private Paint mPaint;
    private int curLayer = -1;
    private int tabW;
    private int txtH;

    private Matrix mMatrix;

    private int[] location = new int[2];

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(factor, factor);
        CanvasManager canvasManager = CanvasManager.getInstance(getContext());
        mStartLayer = canvasManager.getStartLayer();
        mEndLayer = canvasManager.getEndLayer();
        curLayer = -1;
        layerCount(canvas, canvasManager.getViewGroup());
    }

    private void layerCount(Canvas canvas, View view) {
        if (view == null) {
            return;
        }

        curLayer++;
        if (curLayer >= mStartLayer && curLayer <= mEndLayer && !(view instanceof SAKCoverView)) {
            drawLayer(canvas, view);
        }
        if (view instanceof ViewGroup && !(view instanceof SAKCoverView)) {
            ViewGroup vg = ((ViewGroup) view);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                layerCount(canvas, child);
            }
        }
        curLayer--;
    }

    private void drawLayer(Canvas canvas, View view) {
        canvas.translate(0, txtH);
        canvas.save();
        if (view.getVisibility() != VISIBLE) {
            mPaint.setColor(Color.GRAY);
        } else {
            mPaint.setColor(Color.WHITE);
        }
        for (int i = 0; i < curLayer; i++) {
            canvas.translate(tabW, 0);
            canvas.drawText("|", 0, 0, mPaint);
        }
        canvas.translate(tabW, 0);
        String txt = getInfo(view);
        canvas.drawText(txt, 0, 0, mPaint);
        if (view instanceof ImageView) {
            float len = mPaint.measureText(txt);
            canvas.translate(len, 0);
            drawBitmap(canvas, ((ImageView) view));
            canvas.translate(-len, 0);
        }
        canvas.restore();
    }

    private void drawBitmap(Canvas canvas, ImageView view) {
        if (view == null) {
            return;
        }
        Drawable drawable = view.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                if (mMatrix == null) {
                    mMatrix = new Matrix();
                } else {
                    mMatrix.reset();
                }
                float scale = txtH * 1.0f / bmp.getHeight();
                float w = scale * bmp.getWidth();
                canvas.drawText(" -bmp(w:" + bmp.getWidth() + " h:" + bmp.getHeight() + ")", w, 0, mPaint);
                mMatrix.setScale(scale, scale);
                canvas.translate(0, -txtH >> 1);
                canvas.drawBitmap(bmp, mMatrix, mPaint);
                canvas.translate(0, txtH >> 1);
            }
        }

    }

    protected String getInfo(View view) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(view.getClass().getName()).append(" ");

        sb.append("-(w:").append(view.getWidth());
        sb.append(" h:").append(view.getHeight()).append(") ");

        view.getLocationOnScreen(location);
        sb.append(" -loc(x:")
                .append(location[0])
                .append("-")
                .append(location[0] + view.getWidth())
                .append(" y:")
                .append(location[1])
                .append("-")
                .append(location[1] + view.getHeight())
                .append(")");

        sb.append(" -P(l:").append(view.getPaddingLeft()).append(" t:").append(view.getPaddingTop())
                .append(" r:").append(view.getPaddingRight()).append(" b:").append(view.getPaddingBottom()).append(")");

        ViewGroup.LayoutParams param = view.getLayoutParams();
        if (param instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) param);
            sb.append(" -M(l:").append(marginLayoutParams.leftMargin).append(" t:").append(marginLayoutParams.topMargin)
                    .append(" r:").append(marginLayoutParams.rightMargin).append(" b:").append(marginLayoutParams.bottomMargin).append(")");

        }
        int visible = view.getVisibility();
        String visStr = "";
        if (visible == VISIBLE) {
            visStr = "visible";
        } else if (visible == INVISIBLE) {
            visStr = "invisible";
        } else if (visible == GONE) {
            visStr = "gone";
        }

        if (view instanceof TextView) {
            String txt = ((TextView) view).getText().toString();
            sb.append(" -txt:").append(txt);
        }
        sb.append(" -visible:").append(visStr).append(" ");
        sb.append(" -extra:").append(view.getTag(CanvasManager.INFO_KEY));

        return sb.toString();
    }


    private float lastX;
    private float lastY;

    private int mode = 0;
    float oldDist;
    float factor = 1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                mode = 1;
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode -= 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                mode += 1;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode >= 2) {
                    float newDist = spacing(event);
                    zoom(newDist / oldDist);

                } else {
                    float curX = event.getX();
                    float curY = event.getY();
                    scrollBy((int) (lastX - curX), (int) (lastY - curY));
                    lastX = curX;
                    lastY = curY;
                }
                break;
        }

        return true;
    }

    private void zoom(float f) {
        factor = f;
        invalidate();
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    protected int px2dp(float px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        return params;
    }


    @Override
    public void onChange(MotionEvent motionEvent) {

    }
}
