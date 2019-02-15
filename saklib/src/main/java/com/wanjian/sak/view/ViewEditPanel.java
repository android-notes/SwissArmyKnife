package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.Px2SpSizeConverter;

import java.lang.ref.WeakReference;

public class ViewEditPanel extends LinearLayout {
    private TextView title;
    private EditText width;
    private EditText height;
    private EditText weight;
    private EditText weightSum;
    private EditText gravity;
    private EditText ml;
    private EditText mr;
    private EditText mt;
    private EditText mb;
    private EditText pl;
    private EditText pr;
    private EditText pt;
    private EditText pb;
    private EditText size;
    private EditText text;
    private EditText color;
    private EditText backgroundColor;
    private ViewGroup textRow;
    private ViewGroup sizeRow;
    private ViewGroup colorRow;
    private ViewGroup weightRow;
    private ViewGroup weightSumRow;
    private WeakReference<View> targetViewRef;


    public ViewEditPanel(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        init(LayoutInflater.from(context).inflate(R.layout.sak_edit_panel_layout, this, true));
    }

    private void init(View view) {
        title = (TextView) findViewById(R.id.title);
        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
        gravity = (EditText) findViewById(R.id.gravity);
        weight = (EditText) findViewById(R.id.weight);
        weightSum = (EditText) findViewById(R.id.weightSum);
        ml = (EditText) findViewById(R.id.ml);
        mr = (EditText) findViewById(R.id.mr);
        mt = (EditText) findViewById(R.id.mt);
        mb = (EditText) findViewById(R.id.mb);
        pl = (EditText) findViewById(R.id.pl);
        pr = (EditText) findViewById(R.id.pr);
        pt = (EditText) findViewById(R.id.pt);
        pb = (EditText) findViewById(R.id.pb);
        size = (EditText) findViewById(R.id.size);
        color = (EditText) findViewById(R.id.color);
        text = (EditText) findViewById(R.id.text);
        backgroundColor = (EditText) findViewById(R.id.backgroundColor);
        sizeRow = (ViewGroup) findViewById(R.id.sizeRow);
        colorRow = (ViewGroup) findViewById(R.id.colorRow);
        weightRow = (ViewGroup) findViewById(R.id.weightRow);
        weightSumRow = (ViewGroup) findViewById(R.id.weightSumRow);
        textRow = (ViewGroup) findViewById(R.id.textRow);

        findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View targetView = targetViewRef.get();
                if (targetView == null) {
                    return;
                }
                Context context = getContext();
                ViewGroup.LayoutParams params = targetView.getLayoutParams();
                setWidth(context, params);

                setHeight(context, params);


                setWeightIfNeeded(params, targetView);

                if (params instanceof MarginLayoutParams) {
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) params;
                    setMargin(context, marginLayoutParams);
                }

                setPadding(targetView, context);


                if (targetView instanceof TextView) {
                    TextView textView = (TextView) targetView;
                    setTextInfo(context, textView);
                }

                try {
                    int color = Color.parseColor(backgroundColor.getText().toString());
                    targetView.setBackgroundColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                targetView.requestLayout();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

    }

    private void setWeightIfNeeded(ViewGroup.LayoutParams params, View targetView) {
        if (targetView instanceof LinearLayout) {
            try {
                ((LinearLayout) targetView).setWeightSum(Float.parseFloat(weightSum.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (params instanceof LayoutParams) {
            try {
                ((LayoutParams) params).weight = Float.parseFloat(weight.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setHeight(Context context, ViewGroup.LayoutParams params) {
        String heightInput = height.getText().toString();
        if (heightInput.equalsIgnoreCase("M")) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (heightInput.equalsIgnoreCase("W")) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            try {
                params.height = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(heightInput));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setWidth(Context context, ViewGroup.LayoutParams params) {
        String widthInput = width.getText().toString();
        if (widthInput.equalsIgnoreCase("M")) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (widthInput.equalsIgnoreCase("W")) {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            try {
                params.width = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(widthInput));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMargin(Context context, MarginLayoutParams marginLayoutParams) {
        try {
            marginLayoutParams.leftMargin = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(ml.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.rightMargin = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(mr.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.topMargin = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(mt.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.bottomMargin = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(mb.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setTextInfo(Context context, TextView textView) {
        try {
            try {
                int px = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(size.getText().toString()));
                textView.setTextSize(new Px2SpSizeConverter().convert(context, px).getLength());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            textView.setTextColor(Color.parseColor(color.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setText(text.getText());
    }

    private void setPadding(View targetView, Context context) {
        int paddingLeft = targetView.getPaddingLeft();
        try {
            paddingLeft = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(pl.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingRight = targetView.getPaddingRight();
        try {
            paddingRight = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(pr.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingTop = targetView.getPaddingTop();
        try {
            paddingTop = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(pt.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingBottom = targetView.getPaddingBottom();
        try {
            paddingBottom = ISizeConverter.CONVERTER.recovery(context, Float.parseFloat(pb.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        targetView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void attachTargetView(View view) {
        Context context = getContext();
        targetViewRef = new WeakReference<>(view);

        title.setText(view.getClass().getSimpleName());

        ViewGroup.LayoutParams params = view.getLayoutParams();

        getWidthHeight(context, params);

        if (view instanceof LinearLayout) {
            float ws = ((LinearLayout) view).getWeightSum();
            weightSumRow.setVisibility(VISIBLE);
            weightSum.setText(ws + "");
        }

        if (params instanceof LinearLayout.LayoutParams) {
            weightRow.setVisibility(VISIBLE);
            weight.setText(((LayoutParams) params).weight + "");
        }

//        try {
//            Field field = params.getClass().getDeclaredField("gravity");
//            field.setAccessible(true);
//            int gravity = (int) field.get(params);//-1
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (params instanceof MarginLayoutParams) {
            getMargin(((MarginLayoutParams) params), context);
        }

        setPadding(context, view);

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            sizeRow.setVisibility(VISIBLE);
            colorRow.setVisibility(VISIBLE);
            textRow.setVisibility(VISIBLE);
            size.setText(ISizeConverter.CONVERTER.convert(context, textView.getTextSize()).getLength() + "");
            color.setText(String.format("#%08x", textView.getCurrentTextColor()));
            text.setText(textView.getText());
        }

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = drawable.getCurrent();
        }
        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            String txt = String.format("#%08x", color);
            backgroundColor.setText(txt);
        }
    }

    private void setPadding(Context context, View view) {
        pl.setText(ISizeConverter.CONVERTER.convert(context, view.getPaddingLeft()).getLength() + "");
        pr.setText(ISizeConverter.CONVERTER.convert(context, view.getPaddingRight()).getLength() + "");
        pt.setText(ISizeConverter.CONVERTER.convert(context, view.getPaddingTop()).getLength() + "");
        pb.setText(ISizeConverter.CONVERTER.convert(context, view.getPaddingBottom()).getLength() + "");

    }

    private void getMargin(MarginLayoutParams params, Context context) {
        ml.setText(ISizeConverter.CONVERTER.convert(context, params.leftMargin).getLength() + "");
        mr.setText(ISizeConverter.CONVERTER.convert(context, params.rightMargin).getLength() + "");
        mt.setText(ISizeConverter.CONVERTER.convert(context, params.topMargin).getLength() + "");
        mb.setText(ISizeConverter.CONVERTER.convert(context, params.bottomMargin).getLength() + "");
    }

//    private float parseFloat(String txt, float defaultValue) {
//        try {
//            return Float.parseFloat(txt);
//        } catch (Exception e) {
//            return defaultValue;
//        }
//    }

    public void getWidthHeight(Context context, ViewGroup.LayoutParams params) {
        if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            width.setText("W");
        } else if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width.setText("M");
        } else {
            width.setText(ISizeConverter.CONVERTER.convert(context, params.width).getLength() + "");
        }

        if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            height.setText("W");
        } else if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            height.setText("M");
        } else {
            height.setText(ISizeConverter.CONVERTER.convert(context, params.height).getLength() + "");
        }
    }

    //
//    private int parseInt(String txt, int defaultValue) {
//        try {
//            return Integer.parseInt(txt);
//        } catch (Exception e) {
//            return defaultValue;
//        }
//    }
    private OnClickListener listener;

    public void setOnConfirmClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
