package com.wanjian.sak.layerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.ViewGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.Utils;

public class ActivityNameLayerView extends AbsLayerView {

    private final Paint mPaint;
    String actName;
    private Paint dashLinePaint;
    private int radius;

    public ActivityNameLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
        mPaint.setColor(Color.RED);

        dashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashLinePaint.setColor(Color.RED);
        dashLinePaint.setStyle(Paint.Style.STROKE);
        dashLinePaint.setStrokeWidth(dp2px(1));
        radius = dp2px(8);
//        dashLinePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_page_name_icon);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params) {
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        return params;
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_activity_name);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getActivityName();
    }

    protected void getActivityName() {
        Activity activity = Utils.findAct(getRootView());
        if (activity == null) {
            return;
        }
        actName = activity.getClass().getName();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawActivityInfo(canvas);
    }

    protected void drawActivityInfo(Canvas canvas) {
        if (actName == null) {
            return;
        }
        drawInfo(canvas, 0, 0, getWidth(), getHeight(), actName);
    }


    protected void drawInfo(Canvas canvas, int x, int y, int w, int h, String name) {
        canvas.save();

        canvas.drawLine(x, y, x + radius, y, dashLinePaint);
        canvas.drawLine(x, y, x, y + radius, dashLinePaint);

        canvas.drawLine(x + w, y, x + w - radius, y, dashLinePaint);
        canvas.drawLine(x + w, y, x + w, y + radius, dashLinePaint);


        canvas.drawLine(x, y + h, x + radius, y + h, dashLinePaint);
        canvas.drawLine(x, y + h, x, y + h - radius, dashLinePaint);


        canvas.drawLine(x + w, y + h, x + w - radius, y + h, dashLinePaint);
        canvas.drawLine(x + w, y + h, x + w, y + h - radius, dashLinePaint);


        canvas.drawLine(x, y, x + w, y + h, dashLinePaint);
        canvas.drawLine(x + w, y, x, y + h, dashLinePaint);

        TextPaint textPaint = new TextPaint(mPaint);
        textPaint.setTextSize(dp2px(13));
        canvas.translate(x, y);
        StaticLayout staticLayout = new StaticLayout(name, textPaint, (int) (w * 0.8), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

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
        canvas.restore();
    }
}
