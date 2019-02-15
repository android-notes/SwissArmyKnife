package com.wanjian.sak.layerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.wanjian.sak.R;

public class GridLayerView extends AbsLayerView {

    private Paint paint;

    public GridLayerView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color());
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
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int space = space();

        for (int i = space; i < w; i += space) {
            canvas.drawLine(i, 0, i, h, paint);
        }
        for (int i = space; i < h; i += space) {
            canvas.drawLine(0, i, w, i, paint);
        }
    }

    protected int color() {
        return 0x88000000;
    }

    protected int space() {
        return dp2px(5);
    }


}
