package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerTxtAdapter;

/**
 * Created by wanjian on 2017/3/1.
 */

public class ViewDrawPerformanceLayer extends LayerTxtAdapter {
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private String mTime;

    public ViewDrawPerformanceLayer(Context context) {
        super(context);
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_view_draw_performance);
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        View root = view.getRootView();
        if (root.getWidth() == 0 || root.getHeight() == 0) {
            return;
        }
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mCanvas == null) {
            mCanvas = new Canvas(mBitmap);
        }
        if (mBitmap.getWidth() != root.getWidth() || mBitmap.getHeight() != root.getHeight()) {
            mBitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas.setBitmap(mBitmap);
        }
        super.onDraw(canvas, paint, view, startLayer, endLayer);

    }

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {

        if (!(view instanceof ViewGroup)) {
            mCanvas.save();

            long start = System.nanoTime();
            view.draw(mCanvas);
            long end = System.nanoTime();

            mCanvas.restore();
            mTime = String.valueOf((end - start) / 10_000 / 100f);
            super.drawLayer(canvas, paint, view);
        }


    }

    @Override
    protected String getTxt(View view) {
        return mTime;
    }
}
