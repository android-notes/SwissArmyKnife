package com.wanjian.sak.layer.impl_tmp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
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
import com.wanjian.sak.utils.ViewInfo;

public class ViewPickerLayer extends DragLayerView {
    private View mTarget;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mBorderWidth;
    private int mLocation[] = new int[2];
    private View mInfoView;
    private TextView mInfoTextView;

    public ViewPickerLayer(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(dp2px(12));
        mBorderWidth = dp2px(10);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        getLocationOnScreen(mLocation);
                        int x = mLocation[0] + getWidth() / 2;
                        int y = mLocation[1] + getHeight() / 2;
                        View view = findPressView(x, y);
                        getViewInfo(view);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_pick_view);
    }

    @Override
    public String description() {
        return getResources().getString(R.string.sak_pick_view);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(FrameLayout.LayoutParams params) {
        params.gravity = Gravity.CENTER;
        params.height = params.width = dp2px(100);
        return params;
    }


    private void getViewInfo(View view) {
        initWindowIfNeeded();
        mInfoTextView.setText(ViewInfo.get(getContext(), view, getSizeConverter()));
    }

    private void initWindowIfNeeded() {
        if (mInfoView != null) {
            return;
        }
        mInfoView = LayoutInflater.from(getContext()).inflate(R.layout.sak_view_info_layout, null);
        mInfoTextView = (TextView) mInfoView.findViewById(R.id.textView);
        mInfoView.setOnTouchListener(new OnTouchListener() {
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
                    updateWindow(mInfoView, params);
                    lastX = cx;
                    lastY = cy;
                }
                return true;
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = dp2px(300);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        showWindow(mInfoView, params);
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
        canvas.drawCircle(0, 0, radius - mBorderWidth / 2, mPaint);


        mPaint.setColor(0xff48c09e);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(-w / 2, 0, w / 2, 0, mPaint);
        canvas.drawLine(0, -h / 2, 0, h / 2, mPaint);
    }

    protected View findPressView(int x, int y) {
        mTarget = getRootView();
        traversal(mTarget, x, y);
        return mTarget;
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

        mTarget = view;
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
    protected void onDetached(View rootView) {
        super.onDetached(rootView);
        mTarget = null;
        if (mInfoView != null) {
            removeWindow(mInfoView);
        }
        mInfoView = null;
        mInfoTextView = null;
    }
}
