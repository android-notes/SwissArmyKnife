package com.wanjian.sak.canvasimpl.performance;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.AbsCanvas;
import com.wanjian.sak.canvasimpl.adapter.CanvasLayerAdapter;
import com.wanjian.sak.canvasimpl.adapter.CanvasLayerTxtAdapter;

/**
 * Created by wanjian on 2017/3/1.
 */

public class DrawTimeCanvas extends CanvasLayerTxtAdapter {
    private Canvas mCanvas;
    private Bitmap mBitmap;

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
            long start = System.nanoTime();

            view.draw(mCanvas);

            long end = System.nanoTime();

            drawTxt((end - start) / 1000_000f + "ms", canvas, paint, view);
        }


    }
}
