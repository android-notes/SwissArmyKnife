package com.wanjian.sak.layer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.support.DragLayerView;
import com.wanjian.sak.utils.BitmapCreater;

/**
 * Created by wanjian on 2016/11/10.
 */

public class TakeColorView extends DragLayerView {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mBorderWidth;
    private Bitmap mBitmap;
    private int mColor;
    private int mLocation[] = new int[2];
    private TextView mColorValueView;

    public TakeColorView(Context context) {
        super(context);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        setWillNotDraw(false);
        mColorValueView = (TextView) LayoutInflater.from(context).inflate(R.layout.sak_toast_layout, null);
        mColorValueView.setOnTouchListener(new OnTouchListener() {
            private float lastX = 0;
            private float lastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                } else {
                    float cx = event.getRawX();
                    float cy = event.getRawY();
                    WindowManager.LayoutParams params = ((WindowManager.LayoutParams) v.getLayoutParams());
                    params.x += (cx - lastX);
                    params.y += (lastY - cy);
                    updateWindow(mColorValueView, params);
                    lastX = cx;
                    lastY = cy;
                }
                return true;
            }
        });
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(dp2px(12));
        mBorderWidth = dp2px(5);
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
    public ViewGroup.LayoutParams getLayoutParams(FrameLayout.LayoutParams params) {
        params.gravity = Gravity.CENTER;
        params.height = params.width = dp2px(100);
        return params;
    }

    private void onChange(MotionEvent motionEvent) {
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
        getLocationInWindow(mLocation);
        int r = getWidth() / 2;
        if (checkPixelAccess(mLocation[0] + r, mLocation[1] + r, mBitmap)) {
            mColor = mBitmap.getPixel(mLocation[0] + r, mLocation[1] + r);
        }
        SpannableString string = new SpannableString(String.format("#%08X", mColor));
        string.setSpan(new ForegroundColorSpan(0xff48c09e), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new RelativeSizeSpan(2), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mColorValueView.setText(string);
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
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(0xff48c09e);
        canvas.translate(radius, radius);
        canvas.drawCircle(0, 0, radius - mBorderWidth, mPaint);

        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mBorderWidth * 2);
        canvas.drawCircle(0, 0, radius - mBorderWidth * 3, mPaint);

        mPaint.setColor(0xff48c09e);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(-w / 2, 0, -1, 0, mPaint);
        canvas.drawLine(1, 0, w / 2, 0, mPaint);

        canvas.drawLine(0, -1, 0, -h / 2, mPaint);
        canvas.drawLine(0, 1, 0, h / 2, mPaint);


    }


    @Override
    public void onAttached(View view) {
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
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.y = dp2px(60);
        showWindow(mColorValueView, params);
    }

    @Override
    protected void onDetached(View rootView) {
        super.onDetached(rootView);
        removeWindow(mColorValueView);
    }
}
