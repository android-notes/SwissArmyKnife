package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Printer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wanjian.sak.R;
import com.wanjian.sak.support.FPSView;
import com.wanjian.sak.support.PerformanceFetcherView;
import com.wanjian.sak.utils.LoopQueue;

import java.lang.reflect.Field;

public class ViewDrawPerformLayer extends AbsLayer {
    private ViewGroup performanceChartView;
    private PerformanceFetcherView performanceFetcherView;
    private FPSView measureChart;
    private FPSView layoutChart;
    private FPSView drawChart;
    private FPSView touchChart;
    private FPSView handlerChart;
    private Printer originPrinter;

    public ViewDrawPerformLayer(Context context) {
        super(context);
    }

    private LoopQueue<Long> measureQueue = new LoopQueue<>(60);
    private LoopQueue<Long> layoutQueue = new LoopQueue<>(60);
    private LoopQueue<Long> drawQueue = new LoopQueue<>(120);
    private LoopQueue<Long> touchQueue = new LoopQueue<>(120);
    private LoopQueue<Long> handlerQueue = new LoopQueue<>(120);

    @Override
    public String description() {
        return getResources().getString(R.string.sak_performance);
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_performance_icon);
    }

    @Override
    protected void onAttached(View rootView) {
        super.onAttached(rootView);
        measureQueue.clean();
        layoutQueue.clean();
        drawQueue.clean();
        touchQueue.clean();
        handlerQueue.clean();
        if (performanceFetcherView == null) {
            initPerformanceView();
        }
        if (performanceChartView == null) {
            initPerformanceChartView();
        }
        replaceLogger();

        ViewGroup group = ((ViewGroup) rootView);
        View content = group.getChildAt(0);

        group.removeView(content);

        group.addView(performanceFetcherView, 0);

        performanceFetcherView.addView(content);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        showWindow(performanceChartView, params);

    }

    private void replaceLogger() {
        Looper looper = Looper.getMainLooper();
        try {
            Field mLoggingField = Looper.class.getDeclaredField("mLogging");
            mLoggingField.setAccessible(true);
            originPrinter = (Printer) mLoggingField.get(looper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        looper.setMessageLogging(new Printer() {
            boolean start = false;
            long s;

            @Override
            public void println(String x) {
                if (originPrinter != null) {
                    originPrinter.println(x);
                }
                if (start == false) {
                    s = System.currentTimeMillis();
                    start = true;
                } else {
                    start = false;
                    handlerQueue.append(System.currentTimeMillis() - s);
                    handlerChart.update(handlerQueue);
                }
            }
        });
    }

    private void initPerformanceChartView() {
        performanceChartView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.sak_performance_layout, null);
        measureChart = (FPSView) performanceChartView.findViewById(R.id.measureChart);
        layoutChart = (FPSView) performanceChartView.findViewById(R.id.layoutChart);
        drawChart = (FPSView) performanceChartView.findViewById(R.id.drawChart);
        touchChart = (FPSView) performanceChartView.findViewById(R.id.touchChart);
        handlerChart = (FPSView) performanceChartView.findViewById(R.id.handlerChart);

        performanceChartView.findViewById(R.id.measureTitle).setOnClickListener(new OnClickListener() {
            private boolean visibility = false;

            @Override
            public void onClick(View v) {
                visibility = !visibility;
                if (visibility) {
                    measureChart.setVisibility(VISIBLE);
                } else {
                    measureChart.setVisibility(GONE);
                }
            }
        });
        performanceChartView.findViewById(R.id.layoutTitle).setOnClickListener(new OnClickListener() {
            private boolean visibility = false;

            @Override
            public void onClick(View v) {
                visibility = !visibility;
                if (visibility) {
                    layoutChart.setVisibility(VISIBLE);
                } else {
                    layoutChart.setVisibility(GONE);
                }
            }
        });
        performanceChartView.findViewById(R.id.drawTitle).setOnClickListener(new OnClickListener() {
            private boolean visibility = true;

            @Override
            public void onClick(View v) {
                visibility = !visibility;
                if (visibility) {
                    drawChart.setVisibility(VISIBLE);
                } else {
                    drawChart.setVisibility(GONE);
                }
            }
        });
        performanceChartView.findViewById(R.id.touchTitle).setOnClickListener(new OnClickListener() {
            private boolean visibility = true;

            @Override
            public void onClick(View v) {
                visibility = !visibility;
                if (visibility) {
                    touchChart.setVisibility(VISIBLE);
                } else {
                    touchChart.setVisibility(GONE);
                }
            }
        });
        performanceChartView.findViewById(R.id.handlerTitle).setOnClickListener(new OnClickListener() {
            private boolean visibility = true;

            @Override
            public void onClick(View v) {
                visibility = !visibility;
                if (visibility) {
                    handlerChart.setVisibility(VISIBLE);
                } else {
                    handlerChart.setVisibility(GONE);
                }
            }
        });

        performanceChartView.setOnTouchListener(new OnTouchListener() {
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
                    updateWindow(performanceChartView, params);
                    lastX = cx;
                    lastY = cy;
                }
                return true;
            }
        });
    }

    private void initPerformanceView() {
        performanceFetcherView = new PerformanceFetcherView(getContext());
        performanceFetcherView.setPerformanceListener(new PerformanceFetcherView.PerformanceListener() {
            @Override
            public void onMeasure(long duration) {
                measureQueue.append(duration);
                measureChart.update(measureQueue);
            }

            @Override
            public void onLayout(long duration) {
                layoutQueue.append(duration);
                layoutChart.update(layoutQueue);
            }

            @Override
            public void onDraw(long duration) {
                drawQueue.append(duration);
                drawChart.update(drawQueue);
            }

            @Override
            public void onTouch(long duration) {
                touchQueue.append(duration);
                touchChart.update(touchQueue);
            }
        });
    }

    @Override
    protected void onDetached(View rootView) {
        super.onDetached(rootView);
        Looper.getMainLooper().setMessageLogging(originPrinter);
        if (performanceChartView != null) {
            removeWindow(performanceChartView);
        }

        ViewGroup group = (ViewGroup) getRootView();
        for (int i = group.getChildCount() - 1; i > -1; i--) {
            View child = group.getChildAt(i);
            if (child instanceof PerformanceFetcherView) {
                View content = ((PerformanceFetcherView) child).getChildAt(0);
                group.removeView(child);
                ((PerformanceFetcherView) child).removeView(content);
                group.addView(content, 0);
                return;
            }
        }
    }

}
