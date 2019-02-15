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

    public ActivityNameLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
//        mPaint.setColor();
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


    protected int getTextSize() {
        return dp2px(20);
    }

    protected int coverColor() {
        return Color.argb(100, 245, 212, 217);
    }

    protected void drawInfo(Canvas canvas, int x, int y, int w, int h, String name) {

        canvas.save();

        mPaint.setColor(coverColor());
        canvas.drawRect(x, y, x + w, y + h, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(x, y, x + w, y + h, mPaint);
        canvas.drawLine(x + w, y, x, y + h, mPaint);

        TextPaint textPaint = new TextPaint(mPaint);
//        textPaint.setColor(getColor());
        textPaint.setTextSize(getTextSize());
        canvas.translate(x, y);
        //文本宽度不能大于fragment宽度的80%
        StaticLayout staticLayout = new StaticLayout(name, textPaint, (int) (w * 0.8), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

        int txtW = staticLayout.getWidth();
        int txtH = staticLayout.getHeight();
        //文本高度若大于fragment高度80%并且不允许超出绘制区间的话则缩放canvas，保证文本绘制范围在fragment内
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
