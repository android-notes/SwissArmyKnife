//package com.wanjian.sak.layer.adapter;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.view.View;
//
///**
// * Created by wanjian on 2016/10/26.
// * <p>
// * 在view左上角画浅白色背景和文本
// */
//
//public abstract class LayerTxtAdapter extends LayerAdapter {
//    private final Paint mPaint;
//    private Rect mRect = new Rect();
//
//    public LayerTxtAdapter(Context context) {
//        super(context);
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setTextSize(dp2px(10));
//        mPaint.setColor(getColor());
//    }
//
//
//    @Override
//    protected void onDrawLayer(Canvas canvas, View view) {
//        String txt = getTxt(view);
//        if (txt == null) {
//            txt = "";
//        }
//        drawTxt(txt, canvas, view);
//    }
//
//    protected abstract String getTxt(View view);
//
//    private void drawTxt(String txt, Canvas canvas, View view) {
//        mPaint.setTextSize(getTextSize());
//        mPaint.getTextBounds(txt, 0, txt.length(), mRect);
//        mPaint.setColor(getBackgroundColor());
//        canvas.drawRect(0, 0, mRect.width() + 2, mRect.height() + 2, mPaint);
//        mPaint.setColor(getColor());
//        canvas.drawText(txt, 1, 1 + mRect.height(), mPaint);
//    }
//
//    protected int getColor() {
//        return Color.BLACK;
//    }
//
//    protected int getBackgroundColor() {
//        return 0x88ffffff;
//    }
//
//    public float getTextSize() {
//        return dp2px(10);
//    }
//}
