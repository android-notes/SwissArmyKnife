package com.wanjian.sak.layerview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.BitmapCreater;

/**
 * Created by wanjian on 2016/11/10.
 */

public class TakeColorView extends DragLayerView {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int borderWidth;
    private Bitmap mBitmap;
    private int color;
    private int location[] = new int[2];
    private Toast toast;

    public TakeColorView(Context context) {
        super(context);
        init();
        toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(dp2px(12));
        borderWidth = dp2px(5);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onChange(event);
                return true;
            }
        });
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_color_picker_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_take_color);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        if (params instanceof FrameLayout.LayoutParams) {
            ((LayoutParams) params).gravity = Gravity.CENTER;
        }
        params.height = params.width = dp2px(100);
        return params;
    }

    public void onChange(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                draw2Bitmap();
                pickColor();
                break;
            case MotionEvent.ACTION_MOVE:
                pickColor();
                break;
            case MotionEvent.ACTION_UP:
                draw2Bitmap();
                pickColor();
                break;
        }
    }

    private void pickColor() {
        if (mBitmap == null) {
            return;
        }
        getLocationInWindow(location);
        int r = getWidth() / 2;
        if (checkPixelAccess(location[0] + r, location[1] + r, mBitmap)) {
            color = mBitmap.getPixel(location[0] + r, location[1] + r);
        }
        SpannableString string = new SpannableString(String.format("#%08X", color));
        string.setSpan(new ForegroundColorSpan(0xff48c09e), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new RelativeSizeSpan(2), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toast.setText(string);
        toast.show();
        invalidate();
    }

    private void draw2Bitmap() {

        View root = getRootView();
        if (mBitmap == null) {
            mBitmap = BitmapCreater.create(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mBitmap == null) {
            Log.w("SAK", "out of memory....");
            return;
        }
        if (mBitmap.getWidth() < root.getWidth() || mBitmap.getHeight() < root.getHeight()) {
            mBitmap = BitmapCreater.create(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mBitmap == null) {
            Log.w("SAK", "out of memory....");
            return;
        }
        setVisibility(INVISIBLE);
        root.draw(new Canvas(mBitmap));
        setVisibility(VISIBLE);
    }

    private boolean checkPixelAccess(int x, int y, Bitmap bitmap) {
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }

        if (x >= bitmap.getWidth()) {
            return false;
        }
        if (y >= bitmap.getHeight()) {
            return false;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        float radius = w / 2f;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(0xff48c09e);
        canvas.translate(radius, radius);
        canvas.drawCircle(0, 0, radius - borderWidth, mPaint);

        mPaint.setColor(color);
        mPaint.setStrokeWidth(borderWidth * 2);
        canvas.drawCircle(0, 0, radius - borderWidth * 3, mPaint);

        mPaint.setColor(0xff48c09e);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(-w / 2, 0, -1, 0, mPaint);
        canvas.drawLine(1, 0, w / 2, 0, mPaint);

        canvas.drawLine(0, -1, 0, -h / 2, mPaint);
        canvas.drawLine(0, 1, 0, h / 2, mPaint);


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Animator set = AnimatorInflater.loadAnimator(getContext(), R.animator.sak_shake_animator);
        set.setTarget(this);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                draw2Bitmap();
                pickColor();
            }
        });
        set.start();
    }
}
