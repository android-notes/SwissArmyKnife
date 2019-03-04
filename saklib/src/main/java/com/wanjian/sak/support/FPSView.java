package com.wanjian.sak.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wanjian.sak.utils.LoopQueue;

public class FPSView extends View {
    private Paint paint;
    private float density;
    private Path path = new Path();
    private LoopQueue<Long> data;

    public FPSView(@NonNull Context context) {
        this(context, null);
    }

    public FPSView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        density = getRootView().getResources().getDisplayMetrics().density;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(1));
    }

    protected int dp2px(float dip) {
        return (int) (dip * density + 0.5);
    }


    public void update(LoopQueue<Long> data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) {
            return;
        }
        float width = getWidth();
        int height = getHeight();
        float gap = width / (data.maxSize() - 1);
        float x = width - gap * data.size();

        paint.setColor(Color.RED);
        path.reset();
        path.moveTo(x, height);

        data.reset();
        while (data.has()) {
            float y = -dp2px(data.take()) * 2;
            path.lineTo(x, y);
            x += gap;
        }
        canvas.translate(0, height);
        canvas.drawPath(path, paint);

        paint.setColor(Color.GREEN);
        int dp16 = dp2px(16) * 2;
        canvas.drawLine(0, -dp16, width, -dp16, paint);
    }
}
