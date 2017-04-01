package com.wanjian.sak.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/10/23.
 */

public class WheelView extends ListView {
    public WheelView(Context context) {
        super(context);
        init(null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }




    private int mHeight;
    private OnChangeListener mOnChangeListener;

    private void init(AttributeSet attrs) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mHeight = (int) getResources().getDimension(R.dimen.sak_wheel_height);

        setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView;
                if (convertView == null) {
                    textView = new TextView(getContext());
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER);
                    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeight / 3);
                    textView.setLayoutParams(params);
                } else {
                    textView = (TextView) convertView;
                }
                if (position == 0) {
                    textView.setText("");
                } else if (position == getCount()) {
                    textView.setText("");
                } else {
                    textView.setText(String.valueOf(position - 1));
                }
                return textView;
            }
        });
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelView);
            int p = a.getInt(R.styleable.WheelView_sak_default_position, 0);
            setSelection(p);
            a.recycle();

        }
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    View child = getChildAt(1);
                    if (child instanceof TextView) {
                        String txt = ((TextView) child).getText().toString();
                        try {
                            int v = Integer.parseInt(txt);
                            if (child.getTop() < child.getHeight() / 2) {
                                setSelection(v + 1);
                            } else {
                                setSelection(v);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    private Paint mPaint;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int h = getHeight();
        int w = getWidth();
        canvas.drawLine(0, h / 3.0f, w, h / 3.0f, mPaint);
        canvas.drawLine(0, h / 3.0f * 2, w, h / 3.0f * 2, mPaint);
    }

    @Override
    public void setSelection(int position) {
        try {
            super.setSelection(position);
            if (mOnChangeListener != null) {
                mOnChangeListener.onChange(position);
            }
        } catch (Exception e) {
        }
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mOnChangeListener = listener;
    }

    public interface OnChangeListener {
        void onChange(int num);
    }


}
