package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.layerview.LayerView;

/**
 * Created by wanjian on 2016/11/10.
 */

public class TakeColorView extends LayerView {
    public TakeColorView(Context context) {
        super(context);
        init();
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_take_color);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        textH = fontMetrics.bottom - fontMetrics.top;
        textW = (int) mPaint.measureText("#ffffffff");

        params.width = textW * 3;
        params.height = textH * 6;
        return params;
    }


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int textW;
    private int textH;

    private Integer ltColor;
    private Integer rtColor;
    private Integer lbColor;
    private Integer rbColor;

    private Bitmap bitmap;

    private void init() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(dp2px(12));
        setBackgroundResource(R.drawable.sak_take_color_bag);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();

        mPaint.setStyle(Paint.Style.FILL);
        if (ltColor != null) {
            drawColor(canvas, ltColor, 0, 0, w / 2, h / 2);
        }
        if (lbColor != null) {
            drawColor(canvas, lbColor, 0, h / 2, w / 2, h);
        }

        if (rtColor != null) {
            drawColor(canvas, rtColor, w / 2, 0, w, h / 2);
        }
        if (rbColor != null) {
            drawColor(canvas, rbColor, w / 2, h / 2, w, h);
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, w - 1, h - 1, mPaint);


    }

    private void drawColor(Canvas canvas, int color, int l, int t, int r, int b) {
        mPaint.setColor(color);
        canvas.drawRect(l, t, r, b, mPaint);
        if ((~color | 0xff000000) == color) {
            mPaint.setColor(0xffff0000);
        } else {
            mPaint.setColor((~color | 0xff000000));
        }

        canvas.drawText(String.format("#%08x", color), l, t + textH, mPaint);

    }

    public void down(float x, float y) {
        ltColor = null;
        rtColor = null;
        lbColor = null;
        rbColor = null;
    }

    public void up(float x, float y) {

        setVisibility(INVISIBLE);
        try {
            View root = findRootView();
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
            }
            if (bitmap.getWidth() < root.getWidth() || bitmap.getHeight() < root.getHeight()) {
                bitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
            }
            root.draw(new Canvas(bitmap));
            int location[] = new int[2];
            getLocationOnScreen(location);
            try {
                ltColor = bitmap.getPixel(location[0], location[1]);
            } catch (Exception e) {
                ltColor = null;
            }
            try {
                rtColor = bitmap.getPixel(location[0] + getWidth(), location[1]);
            } catch (Exception e) {
                rtColor = null;
            }
            try {
                lbColor = bitmap.getPixel(location[0], location[1] + getHeight());
            } catch (Exception e) {
                lbColor = null;
            }
            try {
                rbColor = bitmap.getPixel(location[0] + getWidth(), location[1] + getHeight());
            } catch (Exception e) {
                rbColor = null;
            }

        } catch (Exception e) {
            ltColor = null;
            rtColor = null;
            lbColor = null;
            rbColor = null;
        }
        setVisibility(VISIBLE);
    }

    private View findRootView() {
        return getRootView();
    }


    @Override
    public void onChange(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down(motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                up(motionEvent.getX(), motionEvent.getY());
                break;
        }
    }
}
