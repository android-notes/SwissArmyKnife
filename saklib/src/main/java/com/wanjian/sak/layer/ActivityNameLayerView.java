package com.wanjian.sak.layer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.Utils;

public class ActivityNameLayerView extends AbsLayer {

    private final Paint mPaint;
    private String mActName;
    private int mRadius;

    public ActivityNameLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(dp2px(1));

        mRadius = dp2px(8);
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_page_name_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_activity_name);
    }

    @Override
    public void onAttached(View view) {
        getActivityName(view);
    }

    protected void getActivityName(View view) {
        Activity activity = Utils.findAct(view);
        if (activity == null) {
            return;
        }
        mActName = activity.getClass().getName();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawActivityInfo(canvas);
    }

    protected void drawActivityInfo(Canvas canvas) {
        if (mActName == null) {
            return;
        }
        drawInfo(canvas, 0, 0, getWidth(), getHeight(), mActName);
    }


    protected void drawInfo(Canvas canvas, int x, int y, int w, int h, String txt) {
        canvas.save();
        drawCorner(canvas, x, y, w, h);

        drawX(canvas, x, y, w, h);

        drawName(canvas, x, y, w, h, txt);
        canvas.restore();
    }

    private void drawName(Canvas canvas, int x, int y, int w, int h, String txt) {
        TextPaint textPaint = new TextPaint(mPaint);
        textPaint.setTextSize(dp2px(13));
        canvas.translate(x, y);
        StaticLayout staticLayout = new StaticLayout(txt, textPaint, (int) (w * 0.8), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

        int txtW = staticLayout.getWidth();
        int txtH = staticLayout.getHeight();
        if (txtH > 0 && txtH > h * 0.8) {
            float scale = h * 0.8f / txtH;
            int realW = (int) (txtW * scale);
            int realH = (int) (txtH * scale);

            canvas.translate((w - realW) / 2, (h - realH) / 2);
            canvas.scale(scale, scale);
        } else {
            canvas.translate((w - txtW) / 2, (h - txtH) / 2);
        }

        staticLayout.draw(canvas);
    }

    private void drawX(Canvas canvas, int x, int y, int w, int h) {
        canvas.drawLine(x, y, x + w, y + h, mPaint);
        canvas.drawLine(x + w, y, x, y + h, mPaint);
    }

    private void drawCorner(Canvas canvas, int x, int y, int w, int h) {
        canvas.drawLine(x, y, x + mRadius, y, mPaint);
        canvas.drawLine(x, y, x, y + mRadius, mPaint);

        canvas.drawLine(x + w, y, x + w - mRadius, y, mPaint);
        canvas.drawLine(x + w, y, x + w, y + mRadius, mPaint);


        canvas.drawLine(x, y + h, x + mRadius, y + h, mPaint);
        canvas.drawLine(x, y + h, x, y + h - mRadius, mPaint);


        canvas.drawLine(x + w, y + h, x + w - mRadius, y + h, mPaint);
        canvas.drawLine(x + w, y + h, x + w, y + h - mRadius, mPaint);
    }
}
