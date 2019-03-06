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

    private Context context;
    private ISizeConverter sizeConverter;
    private int[] mLocation = new int[2];

    private ViewInfo(Context context, ISizeConverter sizeConverter) {
        this.context = context;
        this.sizeConverter = sizeConverter;
    }

    private StringBuilder get(View view) {
        StringBuilder builder = new StringBuilder(500);
        getBaseInfo(view, builder);
        builder.append("\n");

        getWidthHeight(view, builder);
        builder.append("\n");

        getPosition(view, builder);
        builder.append("\n");

        if (view instanceof TextView) {
            getTextViewInfo(((TextView) view), builder);
            builder.append("\n");
        }

        getMargin(view, builder);
        builder.append("\n");

        getPadding(view, builder);
        builder.append("\n");

        getTranslation(view, builder);
        builder.append("\n");

        getRotation(view, builder);
        builder.append("\n");

        getScale(view, builder);

        return builder;
    }


    private void getTextViewInfo(TextView view, StringBuilder builder) {
        builder.append("Text Color\n");
        builder.append("Default: ").append(String.format("#%08X", view.getCurrentTextColor())).append("\n");
        CharSequence charSequence = view.getText();
        if (charSequence instanceof Spanned) {
            getSpanColor(view, builder, ((Spanned) charSequence));
        }
        builder.append("\n");
        Drawable bagDrawable = view.getBackground();
        if (bagDrawable instanceof ColorDrawable) {
            builder.append("Text Background Color\n");
            builder.append("Default: ").append(String.format("#%08X", ((ColorDrawable) bagDrawable).getColor())).append("\n");
        }
        if (charSequence instanceof Spanned) {
            getTextBagColorSpan(view, builder, ((Spanned) charSequence));
        }
        builder.append("\n");
        builder.append("Text Size\n");
        ISizeConverter sizeConverter = getSizeConverter();
        builder.append("Default: ").append(sizeConverter.convert(getContext(), view.getTextSize())).append("\n");
        if (charSequence instanceof Spanned) {
            getTextSizeSpan(view, builder, ((Spanned) charSequence));
        }
    }

    private void getTextSizeSpan(TextView view, StringBuilder builder, Spanned spanned) {
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
        builder.append(sizeConverter.convert(context, (float) firstSizeSpan).getLength());
        for (int i = 1; i < size.length; i++) {
            if (Math.abs(firstSizeSpan - size[i]) < 0.000001) {
                continue;
            }
            firstSizeSpan = size[i];
            builder.append("\n").append(sizeConverter.convert(context, (float) firstSizeSpan).getLength());
        }
        builder.append("\n");
    }

    private void getTextBagColorSpan(TextView view, StringBuilder builder, Spanned spanned) {
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
        for (int i = 1; i < colors.length; i++) {
            if (firstColorSpan != colors[i]) {
                firstColorSpan = colors[i];
                builder.append("\n").append(String.format("#%08X", firstColorSpan));
            }
        }
        builder.append("\n");
    }

    private void getSpanColor(TextView view, StringBuilder builder, Spanned spanned) {
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
        for (int i = 1; i < colors.length; i++) {
            if (firstColorSpan != colors[i]) {
                firstColorSpan = colors[i];
                builder.append("\n").append(String.format("#%08X", firstColorSpan));
            }
        }
        builder.append("\n");
    }

    private void getBaseInfo(View view, StringBuilder builder) {
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

    private void getWidthHeight(View view, StringBuilder builder) {
        Context ctx = getContext();
        ISizeConverter sizeConverter = getSizeConverter();
        builder.append("Width: ").append(sizeConverter.convert(ctx, view.getWidth())).append("\n");
        builder.append("Height: ").append(sizeConverter.convert(ctx, view.getHeight())).append("\n");
    }

    private void getPosition(View view, StringBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Relative Position\n");
        builder.append("X: ").append(sizeConverter.convert(ctx, view.getX())).append("\n");
        builder.append("Y: ").append(sizeConverter.convert(ctx, view.getY())).append("\n");

        builder.append("Absolute Position\n");
        view.getLocationInWindow(mLocation);
        builder.append("X: ").append(sizeConverter.convert(ctx, mLocation[0])).append("\n");
        builder.append("Y: ").append(sizeConverter.convert(ctx, mLocation[1])).append("\n");

    }

    private void getScale(View view, StringBuilder builder) {
        builder.append("Scale X: ").append(view.getScaleX()).append("\n");
        builder.append("Scale Y: ").append(view.getScaleY());

    }

    private void getRotation(View view, StringBuilder builder) {
        builder.append("Rotation: ").append(view.getRotation()).append("\n");

    }

    private void getTranslation(View view, StringBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Translation X: ").append(sizeConverter.convert(ctx, view.getTranslationX())).append("\n");
        builder.append("Translation Y: ").append(sizeConverter.convert(ctx, view.getTranslationY())).append("\n");
    }

    private void getPadding(View view, StringBuilder builder) {
        ISizeConverter sizeConverter = getSizeConverter();
        Context ctx = getContext();
        builder.append("Padding Left: ").append(sizeConverter.convert(ctx, view.getPaddingLeft())).append("\n");
        builder.append("Padding Top: ").append(sizeConverter.convert(ctx, view.getPaddingTop())).append("\n");
        builder.append("Padding Right: ").append(sizeConverter.convert(ctx, view.getPaddingRight())).append("\n");
        builder.append("Padding Bottom: ").append(sizeConverter.convert(ctx, view.getPaddingBottom())).append("\n");
    }

    private void getMargin(View view, StringBuilder builder) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ISizeConverter sizeConverter = getSizeConverter();
            Context ctx = getContext();
            ViewGroup.MarginLayoutParams mgParams = (ViewGroup.MarginLayoutParams) params;
            builder.append("Margin Left: ").append(sizeConverter.convert(ctx, mgParams.leftMargin)).append("\n");
            builder.append("Margin Top: ").append(sizeConverter.convert(ctx, mgParams.topMargin)).append("\n");
            builder.append("Margin Right: ").append(sizeConverter.convert(ctx, mgParams.rightMargin)).append("\n");
            builder.append("Margin Bottom: ").append(sizeConverter.convert(ctx, mgParams.bottomMargin)).append("\n");
        }
    }

    public ISizeConverter getSizeConverter() {
        return sizeConverter;

    }

    public Context getContext() {
        return context;
    }
}
