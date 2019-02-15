package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.adapter.LayerAdapter;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingLayer extends LayerAdapter {
    private final Paint mPaint;
    private Rect mRect = new Rect();

    public PaddingLayer(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(getColor());
    }

    @Override
    protected void drawLayer(Canvas canvas, View view) {

        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        int w = view.getWidth();
        int h = view.getHeight();
        int[] locationSize = getLocationAndSize(view);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0x3300ff00);
        canvas.drawRect(locationSize[0], locationSize[1], locationSize[0] + l, locationSize[1] + h, mPaint);
        canvas.drawRect(locationSize[0], locationSize[1], locationSize[0] + w, locationSize[1] + t, mPaint);
        canvas.drawRect(locationSize[0] + w - r, locationSize[1], locationSize[0] + w, locationSize[1] + h, mPaint);
        canvas.drawRect(locationSize[0], locationSize[1] + h - b, locationSize[0] + w, locationSize[1] + h, mPaint);


        if (l != 0) {
            String txt = "PL" + convertSize(l).getLength();
            mPaint.getTextBounds(txt, 0, txt.length(), mRect);
            mPaint.setColor(0x88ffffff);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0], locationSize[1] + h / 2, locationSize[0] + mRect.width(), locationSize[1] + h / 2 + mRect.height(), mPaint);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0], locationSize[1] + h / 2 + mRect.height(), mPaint);
        }
        if (t != 0) {
            String txt = "PT" + convertSize(t).getLength();
            mPaint.getTextBounds(txt, 0, txt.length(), mRect);
            mPaint.setColor(0x88ffffff);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1], locationSize[0] + w / 2 + mRect.width(), locationSize[1] + mRect.height(), mPaint);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + mRect.height(), mPaint);
        }
        if (r != 0) {
            String txt = "PR" + convertSize(r).getLength();
            mPaint.getTextBounds(txt, 0, txt.length(), mRect);
            mPaint.setColor(0x88ffffff);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w - mRect.width(), locationSize[1] + h / 2, locationSize[0] + w, locationSize[1] + h / 2 + mRect.height(), mPaint);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w - mRect.width(), locationSize[1] + h / 2 + mRect.height(), mPaint);
        }
        if (b != 0) {
            String txt = "PB" + convertSize(b).getLength();
            mPaint.getTextBounds(txt, 0, txt.length(), mRect);
            mPaint.setColor(0x88ffffff);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(locationSize[0] + w / 2, locationSize[1] + h - mRect.height(), locationSize[0] + w / 2 + mRect.width(), locationSize[1] + h, mPaint);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(txt, locationSize[0] + w / 2, locationSize[1] + h, mPaint);
        }
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_padding_icon);
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_padding);
    }
}
