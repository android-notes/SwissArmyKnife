package com.wanjian.sak.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanjian.sak.converter.ISizeConverter;

import java.util.Arrays;

public class ViewInfo {

    public static CharSequence get(Context context, View view, ISizeConverter sizeConverter) {
        return new ViewInfo(context, sizeConverter).get(view);
    }

    private char blockChar = (char) 9608;
    private Context context;
    private ISizeConverter sizeConverter;
    private int[] mLocation = new int[2];

    private ViewInfo(Context context, ISizeConverter sizeConverter) {
        this.context = context;
        this.sizeConverter = sizeConverter;
    }

    private CharSequence get(View view) {
        SpanStrBuilder builder = new SpanStrBuilder();
        if (view instanceof TextView) {
            CharSequence charSequence = ((TextView) view).getText();
            if (charSequence.length() > 50) {
                charSequence = charSequence.subSequence(0, 50);
            }
            builder.append(charSequence).append("\n");
        }
        getBaseInfo(view, builder);
        builder.append("\n");

        getWidthHeight(view, builder);
        builder.append("\n");

        getBackground(view, builder);
        builder.append("\n");

        if (view instanceof TextView) {
            getTextViewInfo(((TextView) view), builder);
            builder.append("\n");
        }

        getPosition(view, builder);
        builder.append("\n");

        getMargin(view, builder);
        builder.append("\n");

        getPadding(view, builder);
        builder.append("\n");

        getTranslation(view, builder);
        builder.append("\n");

        getRotation(view, builder);
        builder.append("\n");

        getScale(view, builder);

        return builder.build();
    }

    private void getBackground(View view, SpanStrBuilder builder) {
        Drawable bagDrawable = view.getBackground();
        if (bagDrawable != null) {
            bagDrawable = bagDrawable.getCurrent();
        }
        if (bagDrawable instanceof ColorDrawable) {
            builder.append("Background Color\n");
            int bagColor = ((ColorDrawable) bagDrawable).getColor();
            ForegroundColorSpan blockSpan = new ForegroundColorSpan(bagColor);
            builder.append(String.format("#%08X", bagColor)).append(blockChar, blockSpan).append("\n");
        }
    }


    private void getTextViewInfo(TextView view, SpanStrBuilder builder) {
        builder.append("Text Color\n");
        int defaultColor = view.getCurrentTextColor();
        ForegroundColorSpan blockSpan = new ForegroundColorSpan(defaultColor);
        builder.append("Default: ").append(String.format("#%08X", defaultColor)).append(blockChar, blockSpan).append("\n");
        CharSequence charSequence = view.getText();
        if (charSequence instanceof Spanned) {
            getSpanColor(view, builder, ((Spanned) charSequence));
        }
        builder.append("\n");
//        Drawable bagDrawable = view.getBackground();
//        if (bagDrawable != null) {
//            bagDrawable = bagDrawable.getCurrent();
//        }
//        if (bagDrawable instanceof ColorDrawable) {
//            builder.append("Text Background Color\n");
//            int bagColor = ((ColorDrawable) bagDrawable).getColor();
//            blockSpan = new ForegroundColorSpan(bagColor);
//            builder.append("Default: ").append(String.format("#%08X", bagColor)).append(blockChar, blockSpan).append("\n");
//        }
        if (charSequence instanceof Spanned) {
            getTextBagColorSpan(view, builder, ((Spanned) charSequence));
        }
        builder.append("\n");
        builder.append("Text Size\n");
        ISizeConverter sizeConverter = getSizeConverter();
        builder.append("Default: ").append(sizeConverter.convert(getContext(), view.getTextSize()).toString()).append("\n");
        if (charSequence instanceof Spanned) {
            getTextSizeSpan(view, builder, ((Spanned) charSequence));
        }
    }

    private void getTextSizeSpan(TextView view, SpanStrBuilder builder, Spanned spanned) {
        if (spanned.length() == 0) {
            return;
        }
        Object[] objects = spanned.getSpans(0, spanned.length(), Object.class);
        if (objects == null || objects.length == 0) {
            return;
        }
        builder.append("Text Size Span\n");
        Context context = getContext();
        ISizeConverter sizeConverter = getSizeConverter();
        double size[] = new double[spanned.length()];
        float density = view.getResources().getDisplayMetrics().density;
        Arrays.fill(size, 0, size.length, view.getTextSize());
        for (Object object : objects) {
            if (object instanceof RelativeSizeSpan) {
                int start = spanned.getSpanStart(object);
                int end = spanned.getSpanEnd(object);
                float scale = ((RelativeSizeSpan) object).getSizeChange();
                for (int i = start; i < end; i++) {
                    size[i] = size[i] * scale;
                }
            } else if (object instanceof AbsoluteSizeSpan) {
                AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) object;
                int start = spanned.getSpanStart(object);
                int end = spanned.getSpanEnd(object);
                Arrays.fill(size, start, end, absoluteSizeSpan.getDip() ? (int) (absoluteSizeSpan.getSize() * density + 0.5) : absoluteSizeSpan.getSize());
            }
        }
        double firstSizeSpan = size[0];
        builder.append(sizeConverter.convert(context, (float) firstSizeSpan).toString());
        for (int i = 1; i < size.length; i++) {
            if (Math.abs(firstSizeSpan - size[i]) < 0.000001) {
                continue;
            }
            firstSizeSpan = size[i];
            builder.append("\n").append(sizeConverter.convert(context, (float) firstSizeSpan).toString());
        }
        builder.append("\n");
    }

    private void getTextBagColorSpan(TextView view, SpanStrBuilder builder, Spanned spanned) {
        Object[] objects = spanned.getSpans(0, spanned.length(), BackgroundColorSpan.class);
        if (objects == null || objects.length == 0) {
            return;
        }
        builder.append("Text Background Span\n");
        int colors[] = new int[spanned.length()];
        for (Object object : objects) {
            int start = spanned.getSpanStart(object);
            int end = spanned.getSpanEnd(object);
            Arrays.fill(colors, start, end, ((BackgroundColorSpan) object).getBackgroundColor());
        }
        int firstColorSpan = colors[0];
        builder.append(String.format("#%08X", firstColorSpan));
        ForegroundColorSpan blockSpan = new ForegroundColorSpan(firstColorSpan);
        builder.append(blockChar, blockSpan);
        for (int i = 1; i < colors.length; i++) {
            if (firstColorSpan != colors[i]) {
                firstColorSpan = colors[i];
                builder.append("\n").append(String.format("#%08X", firstColorSpan));
                blockSpan = new ForegroundColorSpan(firstColorSpan);
                builder.append(blockChar, blockSpan);
            }
        }
        builder.append("\n");
    }

    private void getSpanColor(TextView view, SpanStrBuilder builder, Spanned spanned) {
        Object[] objects = spanned.getSpans(0, spanned.length(), ForegroundColorSpan.class);
        if (objects == null || objects.length == 0) {
            return;
        }
        builder.append("Text Color Span\n");
        int colors[] = new int[spanned.length()];
        Arrays.fill(colors, view.getCurrentTextColor());
        for (Object object : objects) {
            int start = spanned.getSpanStart(object);
            int end = spanned.getSpanEnd(object);
            Arrays.fill(colors, start, end, ((ForegroundColorSpan) object).getForegroundColor());
        }
        int firstColorSpan = colors[0];
        builder.append(String.format("#%08X", firstColorSpan));
        ForegroundColorSpan blockSpan = new ForegroundColorSpan(firstColorSpan);
        builder.append(blockChar, blockSpan);
        for (int i = 1; i < colors.length; i++) {
            if (firstColorSpan != colors[i]) {
                firstColorSpan = colors[i];
                builder.append("\n").append(String.format("#%08X", firstColorSpan));
                blockSpan = new ForegroundColorSpan(firstColorSpan);
                builder.append(blockChar, blockSpan);
            }
        }
        builder.append("\n");
    }

    private void getBaseInfo(View view, SpanStrBuilder builder) {
        builder.append("Type: ").append(view.getClass().getSimpleName()).append("\n");
        final int id = view.getId();
        if (id == View.NO_ID) {
            return;
        }
        builder.append("ID: ");
        Resources r = view.getResources();
        try {
            String pkgName;
            switch (id & 0xff000000) {
                case 0x7f000000:
                    pkgName = "app";
                    break;
                case 0x01000000:
                    pkgName = "android";
                    break;
                default:
                    pkgName = r.getResourcePackageName(id);
                    break;
            }
            String typename = r.getResourceTypeName(id);
            builder.append(pkgName);
            builder.append(":");
            builder.append(typename);
            builder.append("/");
            builder.append(r.getResourceEntryName(id));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        builder.append("\n");

    }

    private void getWidthHeight(View view, SpanStrBuilder builder) {
        Context ctx = getContext();
        ISizeConverter sizeConverter = getSizeConverter();
        builder.append("Width:  ").append(sizeConverter.convert(ctx, view.getWidth()).toString()).append("\n");
        builder.append("Height: ").append(sizeConverter.convert(ctx, view.getHeight()).toString()).append("\n");
    }

    private void getPosition(View view, SpanStrBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Relative Position\n");
        builder.append("X: ").append(sizeConverter.convert(ctx, view.getX()).toString()).append("\n");
        builder.append("Y: ").append(sizeConverter.convert(ctx, view.getY()).toString()).append("\n");

        builder.append("Absolute Position\n");
        view.getLocationInWindow(mLocation);
        builder.append("X: ").append(sizeConverter.convert(ctx, mLocation[0]).toString()).append("\n");
        builder.append("Y: ").append(sizeConverter.convert(ctx, mLocation[1]).toString()).append("\n");

    }

    private void getScale(View view, SpanStrBuilder builder) {
        builder.append("Scale X: ").append(String.valueOf(view.getScaleX())).append("\n");
        builder.append("Scale Y: ").append(String.valueOf(view.getScaleY()));

    }

    private void getRotation(View view, SpanStrBuilder builder) {
        builder.append("Rotation: ").append(String.valueOf(view.getRotation())).append("\n");

    }

    private void getTranslation(View view, SpanStrBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Translation X: ").append(sizeConverter.convert(ctx, view.getTranslationX()).toString()).append("\n");
        builder.append("Translation Y: ").append(sizeConverter.convert(ctx, view.getTranslationY()).toString()).append("\n");
    }

    private void getPadding(View view, SpanStrBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Padding Left:   ").append(sizeConverter.convert(ctx, view.getPaddingLeft()).toString()).append("\n");
        builder.append("Padding Top:    ").append(sizeConverter.convert(ctx, view.getPaddingTop()).toString()).append("\n");
        builder.append("Padding Right:  ").append(sizeConverter.convert(ctx, view.getPaddingRight()).toString()).append("\n");
        builder.append("Padding Bottom: ").append(sizeConverter.convert(ctx, view.getPaddingBottom()).toString()).append("\n");
    }

    private void getMargin(View view, SpanStrBuilder builder) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ISizeConverter sizeConverter = getSizeConverter();
            Context ctx = getContext();
            ViewGroup.MarginLayoutParams mgParams = (ViewGroup.MarginLayoutParams) params;
            builder.append("Margin Left:   ").append(sizeConverter.convert(ctx, mgParams.leftMargin).toString()).append("\n");
            builder.append("Margin Top:    ").append(sizeConverter.convert(ctx, mgParams.topMargin).toString()).append("\n");
            builder.append("Margin Right:  ").append(sizeConverter.convert(ctx, mgParams.rightMargin).toString()).append("\n");
            builder.append("Margin Bottom: ").append(sizeConverter.convert(ctx, mgParams.bottomMargin).toString()).append("\n");
        }
    }

    public ISizeConverter getSizeConverter() {
        return sizeConverter;

    }

    public Context getContext() {
        return context;
    }
}
