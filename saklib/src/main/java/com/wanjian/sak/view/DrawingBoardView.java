package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wanjian on 2016/10/23.
 */

public class DrawingBoardView extends View {
    private Bitmap mBitmap;

    public DrawingBoardView(Context context) {
        super(context);
    }


    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    public void setInfo(Bitmap info) {
        mBitmap = info;
//        invalidate();
    }

}
