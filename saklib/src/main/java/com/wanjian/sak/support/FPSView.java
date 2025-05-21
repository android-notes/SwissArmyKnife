package com.wanjian.sak.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        paint.setTextSize(dp2px(10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(0.8f));
    }

    protected int dp2px(float dip) {
        return (int) (dip * density + 0.5);
    }


    public void update(LoopQueue<Long> data) {
        this.data = data;
        postInvalidate();
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
        canvas.drawText("16", 5, -dp16, paint);

        paint.setColor(Color.RED);
        int dp30 = dp2px(30) * 2;
        canvas.drawLine(0, -dp30, width, -dp30, paint);
        canvas.drawText("30", 5, -dp30, paint);

        paint.setColor(Color.MAGENTA);
        int dp50 = dp2px(50) * 2;
        canvas.drawLine(0, -dp50, width, -dp50, paint);
        canvas.drawText("50", 5, -dp50, paint);
    }
}
