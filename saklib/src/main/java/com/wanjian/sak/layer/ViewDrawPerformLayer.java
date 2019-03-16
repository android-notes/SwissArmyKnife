package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
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
    private Handler performanceUIThread;

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
        if (performanceChartView == null) {
            initPerformanceChartView();
        }
        initPerformanceThread();

        if (performanceFetcherView == null) {
            initPerformanceView();
        }

        replaceLogger();

        ViewGroup group = ((ViewGroup) rootView);
        View content = group.getChildAt(0);

        group.removeView(content);

        group.addView(performanceFetcherView, 0);

        performanceFetcherView.addView(content);

    }

    private void initPerformanceThread() {
        /*
        主线程监听到onMeasure，onLayout等调用以及main looper分发消息时，会调用性能统计view的invalidate()更新折线图，
        从而导致main looper再次分发消息，如此反复，导致handler性能折线图一直在变化，然而这些变化其实是由于sak自身导致的，
        并非应用本身的主线程一直在执行消息。为解决该问题，把性能view的添加、更新、移除都放到了子线程中（性能view的触摸事件
        自然也就在子线程执行了），这些相关的消息都在子线程的looper中执行，保证了不干扰主线程的looper
         */
        HandlerThread handlerThread = new HandlerThread("sak-performance-ui-thread");
        handlerThread.start();
        performanceUIThread = new Handler(handlerThread.getLooper());
        performanceUIThread.post(new Runnable() {
            @Override
            public void run() {
                measureQueue.clean();
                layoutQueue.clean();
                drawQueue.clean();
                touchQueue.clean();
                handlerQueue.clean();

                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.format = PixelFormat.RGBA_8888;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                postAddWindowIfRemoving(params);
            }
        });
    }

    /**
     * performanceChartView可能还未移除掉，可能会调用多次
     *
     * @param params
     */
    private void postAddWindowIfRemoving(final WindowManager.LayoutParams params) {
        try {
            showWindow(performanceChartView, params);
        } catch (IllegalStateException e) {
            /*
            e:
             java.lang.IllegalStateException: View android.widget.LinearLayout{33978b6 V.ED..... ........ 0,0-1080,1347} has already been added to the window manager.
                at android.view.WindowManagerGlobal.addView(WindowManagerGlobal.java:328)
                at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:93)
             */
            Log.w("SAK", "performance view is removing...", e);
            performanceUIThread.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postAddWindowIfRemoving(params);
                }
            }, 32);
        }

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
                    performanceUIThread.post(new Runnable() {
                        @Override
                        public void run() {
                            handlerQueue.append(System.currentTimeMillis() - s);
                            handlerChart.update(handlerQueue);
                        }
                    });
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
            public void onMeasure(final long duration) {
                performanceUIThread.post(new Runnable() {
                    @Override
                    public void run() {
                        //LoopQueue不是线程安全的，只能在同一个线程中操作LoopQueue
                        measureQueue.append(duration);
                        measureChart.update(measureQueue);
                    }
                });
            }

            @Override
            public void onLayout(final long duration) {
                performanceUIThread.post(new Runnable() {
                    @Override
                    public void run() {
                        layoutQueue.append(duration);
                        layoutChart.update(layoutQueue);
                    }
                });
            }

            @Override
            public void onDraw(final long duration) {
                performanceUIThread.post(new Runnable() {
                    @Override
                    public void run() {
                        drawQueue.append(duration);
                        drawChart.update(drawQueue);
                    }
                });
            }

            @Override
            public void onTouch(final long duration) {
                performanceUIThread.post(new Runnable() {
                    @Override
                    public void run() {
                        touchQueue.append(duration);
                        touchChart.update(touchQueue);
                    }
                });
            }
        });
    }

    @Override
    protected void onDetached(View rootView) {
        super.onDetached(rootView);
        Looper.getMainLooper().setMessageLogging(originPrinter);
        if (performanceChartView != null) {
            performanceUIThread.post(new Runnable() {
                @Override
                public void run() {
                    //在添加performanceChartView的子线程移除performanceChartView
                    removeWindow(performanceChartView);
                }
            });
        }
        final Looper performanceLooper = performanceUIThread.getLooper();
        //保证所有消息都执行完后才结束线程
        performanceUIThread.post(new Runnable() {
            @Override
            public void run() {
                performanceLooper.quit();
            }
        });

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
