package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;

/**
 * Created by wanjian on 2017/3/1.
 */
@Deprecated
public class PageDrawPerformanceLayer extends AbsLayer {
    private final Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;


    public PageDrawPerformanceLayer(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(getColor());
    }


    @Override
    public String description() {
        return mContext.getString(R.string.sak_page_draw_performance);
    }

    @Override
    protected void onDraw(Canvas canvas, View view) {
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

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(view.getContext().getResources().getDimension(R.dimen.sak_txt_size));
//        long duration = 0;
//        ViewGroup viewGroup = ((ViewGroup) view);
//        for (int i = viewGroup.getChildCount() - 1; i > -1; i--) {
//            View child = viewGroup.getChildAt(i);
//            if (child instanceof SAKCoverView) {
//                continue;
//            }
//            mCanvas.save();
//            long start = System.nanoTime();
//
//            child.draw(mCanvas);
//
//            duration += (System.nanoTime() - start);
//            mCanvas.restore();
//        }

        View content = view.findViewById(android.R.id.content);
        if (content == null) {
            return;
        }
        mCanvas.save();

        long start = System.nanoTime();
        content.draw(mCanvas);
        long duration = System.nanoTime() - start;

        mCanvas.restore();

        canvas.drawText(String.valueOf(duration / 10_000 / 100f), 10, root.getHeight() - 10, mPaint);
    }


}
