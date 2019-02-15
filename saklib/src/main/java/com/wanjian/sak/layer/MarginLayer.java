package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class MarginLayer extends LayerAdapter {

    private final Paint mPaint;
    private Rect mRect = new Rect();

    public MarginLayer(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(getColor());
    }

    @Override
    protected void drawLayer(Canvas canvas, View view) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {//画 外边距
            int[] locationSize = getLocationAndSize(view);
            int w = view.getWidth();
            int h = view.getHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(0x33ff0000);
            int l = locationSize[0] - marginLayoutParams.leftMargin;
            int t = locationSize[1] - marginLayoutParams.topMargin;
            int r = locationSize[0] + w + marginLayoutParams.rightMargin;
            int b = locationSize[1] + h + marginLayoutParams.bottomMargin;

            canvas.drawRect(l, locationSize[1], locationSize[0], locationSize[1] + h, mPaint);//left
            canvas.drawRect(locationSize[0] + w, locationSize[1], r, locationSize[1] + h, mPaint);//right
            canvas.drawRect(locationSize[0], t, locationSize[0] + w, locationSize[1], mPaint);
            canvas.drawRect(locationSize[0], locationSize[1] + h, locationSize[0] + w, b, mPaint);


            if (marginLayoutParams.leftMargin != 0) {
                String txt = "ML" + convertSize(marginLayoutParams.leftMargin).getLength();
                mPaint.getTextBounds(txt, 0, txt.length(), mRect);
                mPaint.setColor(0x88ffffff);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(l, locationSize[1] + locationSize[3] / 2, l + mRect.width(), locationSize[1] + locationSize[3] / 2 + mRect.height(), mPaint);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(txt, l, locationSize[1] + locationSize[3] / 2 + mRect.height(), mPaint);
            }
            if (marginLayoutParams.topMargin != 0) {
                String txt = "MT" + convertSize(marginLayoutParams.topMargin).getLength();
                mPaint.getTextBounds(txt, 0, txt.length(), mRect);
                mPaint.setColor(0x88ffffff);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locationSize[0] + locationSize[2] / 2, t, locationSize[0] + locationSize[2] / 2 + mRect.width(), t + mRect.height(), mPaint);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(txt, locationSize[0] + locationSize[2] / 2, t + mRect.height(), mPaint);
            }

            if (marginLayoutParams.rightMargin != 0) {
                String txt = "MR" + convertSize(marginLayoutParams.rightMargin).getLength();
                mPaint.getTextBounds(txt, 0, txt.length(), mRect);
                mPaint.setColor(0x88ffffff);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(r - mRect.width(), locationSize[1] + locationSize[3] / 2, r, locationSize[1] + locationSize[3] / 2 + mRect.height(), mPaint);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(txt, r - mRect.width(), locationSize[1] + locationSize[3] / 2 + mRect.height(), mPaint);
            }

            if (marginLayoutParams.bottomMargin != 0) {
                String txt = "MB" + convertSize(marginLayoutParams.bottomMargin).getLength();
                mPaint.getTextBounds(txt, 0, txt.length(), mRect);
                mPaint.setColor(0x88ffffff);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(locationSize[0] + locationSize[2] / 2, b - mRect.height(), locationSize[0] + locationSize[2] / 2 + mRect.width(), b, mPaint);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(txt, locationSize[0] + locationSize[2] / 2, b, mPaint);
            }


        }
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_margin_icon);
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_margin);
    }
}
