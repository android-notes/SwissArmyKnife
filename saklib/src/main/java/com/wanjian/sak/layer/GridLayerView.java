package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.wanjian.sak.R;

public class GridLayerView extends AbsLayer {

    private Paint mPaint;

    public GridLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color());
        setWillNotDraw(false);
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_grid_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_grid);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int space = space();

        for (int i = space; i < w; i += space) {
            canvas.drawLine(i, 0, i, h, mPaint);
        }
        for (int i = space; i < h; i += space) {
            canvas.drawLine(0, i, w, i, mPaint);
        }
    }

    protected int color() {
        return 0x55000000;
    }

    protected int space() {
        return dp2px(5);
    }


}
