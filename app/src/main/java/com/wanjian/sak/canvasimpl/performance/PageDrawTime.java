package com.wanjian.sak.canvasimpl.performance;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.AbsCanvas;
import com.wanjian.sak.R;
import com.wanjian.sak.view.ContaierView;

/**
 * Created by wanjian on 2017/3/1.
 */

public class PageDrawTime extends AbsCanvas {
    private Canvas mCanvas;
    private Bitmap mBitmap;

    Paint mPaint;


    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        if (!(view instanceof ViewGroup)) {
            return;
        }
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


        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(view.getContext().getResources().getDimension(R.dimen.sak_txt_size));
        }
        long duration = 0;
        // view.findViewById(android.R.id.content).draw(mCanvas);
        ViewGroup viewGroup = ((ViewGroup) view);
        for (int i = viewGroup.getChildCount() - 1; i > -1; i--) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ContaierView) {
                continue;
            }
            long start = System.nanoTime();

            child.draw(mCanvas);

            duration += (System.nanoTime() - start);
        }


        String s = duration / 1000_000f + "ms";


        float w = mPaint.measureText(s);

        canvas.drawText(s, (root.getWidth() - w) / 2, root.getHeight() / 2, mPaint);
    }


}
