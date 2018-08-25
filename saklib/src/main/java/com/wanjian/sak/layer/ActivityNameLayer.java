package com.wanjian.sak.layer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.RunningActivityFetcher;

import java.lang.ref.WeakReference;
import java.util.List;

public class ActivityNameLayer extends AbsLayer {


    public ActivityNameLayer(Context context) {
        super(context);
    }


    @Override
    public String description() {
        return mContext.getString(R.string.sak_activity_name);
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        if (isEnable() == false) {
            return;
        }
        Activity activity = findAct(view);
        if (activity == null) {
            return;
        }

        onDrawActInfo(activity, canvas, paint, view);
    }

    protected void onDrawActInfo(Activity activity, Canvas canvas, Paint paint, View view) {
        String actName = activity.getClass().getName();
        drawInfo(canvas, paint, 0, 0, view.getWidth(), view.getHeight(), actName);
    }

    private Activity findAct(View view) {
        List<WeakReference<Activity>> references = RunningActivityFetcher.fetch();
        if (references == null) {
            return null;
        }
        for (WeakReference<Activity> act : references) {
            Activity activity = act.get();
            if (activity == null) {
                continue;
            }
            if (view == activity.getWindow().getDecorView().getRootView()) {
                return activity;
            }
        }
        return null;
    }

    protected int getTextSize() {
        return dp2px(20);
    }

    protected int coverColor() {
        return Color.argb(100, 245, 212, 217);
    }

    protected void drawInfo(Canvas canvas, Paint paint, int x, int y, int w, int h, String name) {

        canvas.save();

        paint.setColor(coverColor());
        canvas.drawRect(x, y, x + w, y + h, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(x, y, x + w, y + h, paint);
        canvas.drawLine(x + w, y, x, y + h, paint);

        TextPaint textPaint = new TextPaint(paint);
        textPaint.setColor(getColor());
        textPaint.setTextSize(getTextSize());
        canvas.translate(x, y);
        //文本宽度不能大于fragment宽度的80%
        StaticLayout staticLayout = new StaticLayout(name, textPaint, (int) (w * 0.8), Layout.Alignment.ALIGN_CENTER, 1, 0, false);

        int txtW = staticLayout.getWidth();
        int txtH = staticLayout.getHeight();
        //文本高度若大于fragment高度80%并且不允许超出绘制区间的话则缩放canvas，保证文本绘制范围在fragment内
        if (txtH > 0 && txtH > h * 0.8 && isDrawIfOutBounds() == false) {
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
