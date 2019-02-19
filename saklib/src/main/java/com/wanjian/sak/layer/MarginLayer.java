package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.adapter.LayerAdapter;

import java.text.DecimalFormat;


/**
 * Created by wanjian on 2016/10/23.
 */

public class MarginLayer extends LayerAdapter {

    private final Paint mTextPaint;
    private final Paint mBgPaint;
    private final Paint mMarginPaint;
    private final int mTxtColor = 0xFF000000;
    private final int mBgColor = 0x88FFFFFF;
    private final int mMarginColor = 0x33FF0008;
    private Rect mRect = new Rect();
    private DecimalFormat mFormat = new DecimalFormat("#.###");

    public MarginLayer(Context context) {
        super(context);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(dp2px(10));
        mTextPaint.setColor(mTxtColor);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);

        mMarginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarginPaint.setColor(mMarginColor);
    }

    @Override
    protected void onDrawLayer(Canvas canvas, View view) {
        if (view.getRootView() == view) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {//画 外边距
            int w = view.getWidth();
            int h = view.getHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) params);


            int l = -marginLayoutParams.leftMargin;
            int t = -marginLayoutParams.topMargin;
            int r = w + marginLayoutParams.rightMargin;
            int b = h + marginLayoutParams.bottomMargin;

            canvas.drawRect(l, 0, 0, h, mMarginPaint);//left
            canvas.drawRect(w, 0, r, h, mMarginPaint);//right
            canvas.drawRect(0, t, w, 0, mMarginPaint);
            canvas.drawRect(0, h, w, b, mMarginPaint);
            ISizeConverter converter = getSizeConverter();
            Context context = getContext();

            if (marginLayoutParams.leftMargin != 0) {
                String txt = "L" + mFormat.format(converter.convert(context, marginLayoutParams.leftMargin).getLength());
                mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
                canvas.drawRect(l, h / 2, l + mRect.width(), h / 2 + mRect.height(), mBgPaint);
                canvas.drawText(txt, l, h / 2 + mRect.height(), mTextPaint);
            }
            if (marginLayoutParams.topMargin != 0) {
                String txt = "T" + mFormat.format(converter.convert(context, marginLayoutParams.topMargin).getLength());
                mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
                canvas.drawRect(w / 2, t, w / 2 + mRect.width(), t + mRect.height(), mBgPaint);
                canvas.drawText(txt, w / 2, t + mRect.height(), mTextPaint);
            }

            if (marginLayoutParams.rightMargin != 0) {
                String txt = "R" + mFormat.format(converter.convert(context, marginLayoutParams.rightMargin).getLength());
                mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
                canvas.drawRect(r - mRect.width(), h / 2, r, h / 2 + mRect.height(), mBgPaint);
                canvas.drawText(txt, r - mRect.width(), h / 2 + mRect.height(), mTextPaint);
            }

            if (marginLayoutParams.bottomMargin != 0) {
                String txt = "B" + mFormat.format(converter.convert(context, marginLayoutParams.bottomMargin).getLength());
                mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
                canvas.drawRect(w / 2, b - mRect.height(), w / 2 + mRect.width(), b, mBgPaint);
                canvas.drawText(txt, w / 2, b, mTextPaint);
            }


        }
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_margin_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_margin);
    }
}
